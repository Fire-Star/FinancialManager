package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.po.User;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.*;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class SupplierController {

    private String errorType = "supplierErrorType";

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EquipmentStateService equipmentStateService;

    @Autowired
    private FixedLogService fixedLogService;

    @RequestMapping("/supplier/findAllSupplier")
    public @ResponseBody List<SupplierCustom> showAllSupplier() throws Exception {
        return  supplierService.findAllSupplier();
    }

    @RequestMapping("/supplier/add")
    public void addSingleSupplier(HttpServletRequest request, HttpServletResponse response){
        SupplierCustom supplierCustom = SimpleBeanUtils.setMapPropertyToBean(SupplierCustom.class,request.getParameterMap());
        try {
            supplierCustom.setContractTime(StringUtils.zhDateStrToENDateStr(supplierCustom.getContractTime()));
            supplierService.addSingleSupplier(supplierCustom);
        } catch (Exception e) {
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/supplier/addPage")
    public String addSupplier(){
        return "/WEB-INF/pages/supplier/supplierAdd.html";
    }
    @RequestMapping("/user/supplier/query")
    public String testSupplierquery(){
        return "/WEB-INF/pages/supplier/supplierquery.html";
    }
    @RequestMapping("/user/supplier/detail")
    public String searchSupplierDetail() throws Exception{
        return "/WEB-INF/pages/supplier/supplierdetail.html";
    }
    @RequestMapping("/user/supplier/findSuppBySuppID")
    public  void findSuppBySuppID(HttpServletResponse response,HttpServletRequest request) throws Exception{
        System.out.println
                ("供应商详情界面，该供应商详情panel模块的数据加载......");
        String detailId = "";
        if(request.getParameter("suppId") != null){
            detailId = request.getParameter("suppId");
        }
        System.out.println("suppId:"+detailId);
        SupplierCustom supplierCustom = new SupplierCustom();
        try {
            supplierCustom = supplierService.findSupplierById(detailId);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.fromObject(supplierCustom);
        String detail = JSONObject.fromObject(supplierCustom).toString();
        System.out.println("suppdetail:"+detail);
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }
    @RequestMapping("/user/supplier/search")
    public void searchSupplier(HttpServletResponse response,HttpServletRequest request){
        String limit = "";
        String offset = "";
        String suppliername = "";
        String suppliercontactname = "";
        String contacttel = "";
        String time = "";
        if(request.getParameter("limit") != null ){
            limit = request.getParameter("limit");
        }
        if(request.getParameter("offset") != null ){
            offset = request.getParameter("offset");
        }
        if(request.getParameter("suppliername") != null ){
            suppliername = request.getParameter("suppliername");
        }
        if(request.getParameter("suppliercontactname") != null ){
            suppliercontactname = request.getParameter("suppliercontactname");
        }
        if(request.getParameter("contacttel") != null ){
            contacttel = request.getParameter("contacttel");
        }
        if(request.getParameter("time") != null ){
            time = request.getParameter("time");
        }
        System.out.println("limit:"+limit+"   "+"offset:"+offset+"   "+"suppliername:"+suppliername+"   "+"suppliercontactname:"+suppliercontactname+"   "+"contacttel:"+contacttel+"   "+"time:"+time+"   ");
        String sql = "";
        String sqltemp = "";
        //sql = "SELECT `name`,adtitude,address,contact_name,tel,business,contract_time,custom_message FROM supplier WHERE tel = 13822221111";
        if(!suppliername.equals("")||!suppliercontactname.equals("")||!contacttel.equals("")||!time.equals("")){
            sqltemp = "SELECT id,`name`,adtitude,address,contact_name,tel,business,contract_time,custom_message FROM supplier WHERE ";
        }
        if(!suppliername.equals("")){
            sqltemp = sqltemp + "name= " + suppliername +" ";
        }
        if(!suppliercontactname.equals("")){
            sqltemp = sqltemp + "and contact_name=" + suppliercontactname +" ";
        }
        if(!contacttel.equals("")){
            sqltemp = sqltemp + "and tel=" + contacttel +" ";
        }
        if(!time.equals("")){
            sqltemp = sqltemp + "and contract_time=" + time +" ";
        }
        if(sqltemp.contains("WHERE and")){
            sql = sqltemp.replaceAll("WHERE and","WHERE");
        }else {
            sql = sqltemp;
        }
        System.out.println(sql);
        List<SupplierCustom> listSupplier = null;
        try {
            if(!sql.equals("")&&sql!=null){
                listSupplier = supplierService.findSupplierBySql(sql);
            }else{
                listSupplier = supplierService.findAllSupplier();
            }
        } catch (Exception e) {
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
       // SimpleException.sendSuccessMessage(response,objectMapper);
        List<Object> list = new ArrayList<Object>();
        for(int i=0;i<listSupplier.size();i++){
            Map<String, Object> mapp = new HashMap<String, Object>();
            mapp.put("index", i+"");
            mapp.put("name",listSupplier.get(i).getName());
            mapp.put("contactName",listSupplier.get(i).getContactName());
            mapp.put("tel",listSupplier.get(i).getTel());
            mapp.put("business",listSupplier.get(i).getBusiness());
            mapp.put("count","151545");
            mapp.put("money","21321");
            mapp.put("id",listSupplier.get(i).getId());
            list.add(mapp);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("/user/supplier/findEquipBySuppId")
    public void findEquipBySuppId(HttpServletRequest request,HttpServletResponse response){
        System.out.println("供应商详情界面，该供应商采购设备table模块的数据加载......");
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //通过spring security获得登录的用户名
        UserCustom userCustom = new UserCustom();
        try {
            userCustom = userService.findUserByName(userDetails.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        String role = userRoleService.findRoleByUserName(userCustom.getUsername());
        String city = "";
        try {
            city = userService.findCityByUserName(userCustom.getUsername());
        }catch (Exception e){
            e.printStackTrace();
            city = "";
        }
        String suppId = "";
        if(!"".equals(request.getParameter("suppId"))&&request.getParameter("suppId")!=null){
            suppId = request.getParameter("suppId");
        }
        System.out.println("suppId:"+suppId);
        String sql ="";
        String sqltemp = "SELECT eq_id as eqId,eq_type as eqType,eq_name as eqName,brand_name as brandName,department.department as purchasDepart,department.department as belongDepart,purchas_date as purchasTime,supplier as supplier,eq_state.state as eqStateId,purchas_price as purchasPrice,equipment.custom_message as customMessage,eq_other_id as eqOtherId,equipment.city as city FROM equipment,eq_state,department,supplier WHERE eq_state.eq_state_id=equipment.eq_state AND equipment.purchas_depart=department.id AND equipment.belong_depart=department.id AND supplier.name = equipment.supplier AND supplier.id = '" + suppId +"'";
        if(!"ROLE_ADMIN".equals(role)&&!"".equals(city)){
            sql = sqltemp + " AND equipment.city = '" + city + "'";
        }else{
            sql = sqltemp;
        }
        List<Object> list = new ArrayList<Object>();
        List<EquipmentCustom> equipmentCustomList = new ArrayList<EquipmentCustom>();
        try {
            equipmentCustomList = equipmentService.findAllBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < equipmentCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            map.put("id",equipmentCustomList.get(i).getEqId());
            map.put("equipID",equipmentCustomList.get(i).getEqOtherId());
            map.put("equipType",equipmentCustomList.get(i).getEqType());
            map.put("equipName",equipmentCustomList.get(i).getEqName());
            map.put("brand",equipmentCustomList.get(i).getBrandName());
            map.put("purchasDepart",equipmentCustomList.get(i).getPurchasDepart());
            map.put("belongDepart",equipmentCustomList.get(i).getBelongDepart());
            map.put("state",equipmentCustomList.get(i).getEqStateId());
            map.put("purchasDate",equipmentCustomList.get(i).getPurchasTime());
            map.put("purchasPrice",equipmentCustomList.get(i).getPurchasPrice());
            String month = "";
            int num = 0;
            try {
                num = fixedLogService.countByEqId(equipmentCustomList.get(i).getEqId());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("fixTime",num+"");
            try {
                month = StringUtils.getMonthSpace(equipmentCustomList.get(i).getPurchasTime()) + "";
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("useTime",month);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
