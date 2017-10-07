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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println("供应商查询界面，供应商信息Table模块的加载......");
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
        if(request.getParameter("time") != null && !"".equals(request.getParameter("time"))){
            time = StringUtils.zhDateStrToENDateStr(request.getParameter("time"));
        }
        System.out.println("limit:"+limit+"   "+"offset:"+offset+"   "+"suppliername:"+suppliername+"   "+"suppliercontactname:"+suppliercontactname+"   "+"contacttel:"+contacttel+"   "+"time:"+time+"   ");
        String sql = "";
        //String sqltemp = "SELECT id,`name`,adtitude,address,contact_name,tel,business,contract_time,custom_message FROM supplier";
        String sqltemp = "SELECT id,`name`, adtitude, address, contact_name contactName, tel, business, contract_time" +
                " contractTime, custom_message customMessage FROM supplier";
        if(!suppliername.equals("")||!suppliercontactname.equals("")||!contacttel.equals("")||!time.equals("")){
            sqltemp = sqltemp + " WHERE";
        }
        if(!suppliername.equals("")){
            sqltemp = sqltemp + " name= '" + suppliername +"'";
        }
        if(!suppliercontactname.equals("")){
            sqltemp = sqltemp + " and contact_name like'%" + suppliercontactname +"%'";
        }
        if(!contacttel.equals("")){
            sqltemp = sqltemp + " and tel like'%" + contacttel +"%'";
        }
        if(!time.equals("")){
            sqltemp = sqltemp + " and contract_time='" + time +"'";
        }
        if(sqltemp.contains("WHERE and")){
            sql = sqltemp.replaceAll("WHERE and","WHERE");
        }else {
            sql = sqltemp;
        }
        System.out.println("sql:"+sql);
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
        System.out.println("listSupplier:"+JSONArray.fromObject(listSupplier).toString());
        List<Object> list = new ArrayList<Object>();
        for(int i=0;i<listSupplier.size();i++){
            Map<String, Object> mapp = new HashMap<String, Object>();
            mapp.put("index", i+"");
            mapp.put("name",listSupplier.get(i).getName());
            mapp.put("contactName",listSupplier.get(i).getContactName());
            mapp.put("tel",listSupplier.get(i).getTel());
            mapp.put("business",listSupplier.get(i).getBusiness());
            int supNum = 0;
            try {
                supNum = supplierService.countEqNumBySupName(listSupplier.get(i).getName());
            }catch (Exception e){
                e.printStackTrace();
            }
            mapp.put("count",supNum+"");
            Double money = 0.0;
            try {
                money = supplierService.sumTotalMoney(listSupplier.get(i).getName());
            }catch (Exception e){
                e.printStackTrace();
            }
            mapp.put("money",""+money);
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
            city = userService.findCityIdByUserName(userCustom.getUsername());
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
        System.out.println("sql:"+sql);
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
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }

    @RequestMapping("/supplier/editSupDetail")
    public void editSupDetail(HttpServletRequest request,HttpServletResponse response){
        System.out.println("编辑运营商详细信息......");
        String supId = "";
        String supName = "";
        String supCont = "";
        String supTel = "";
        String supDate = "";
        if(!"".equals(request.getParameter("supId"))&&request.getParameter("supId")!=null){
            supId = request.getParameter("supId");
        }
        if(!"".equals(request.getParameter("supName"))&&request.getParameter("supName")!=null){
            supName = request.getParameter("supName");
        }
        if(!"".equals(request.getParameter("supCont"))&&request.getParameter("supCont")!=null){
            supCont = request.getParameter("supCont");
        }
        if(!"".equals(request.getParameter("supTel"))&&request.getParameter("supTel")!=null){
            supTel = request.getParameter("supTel");
        }
        if(!"".equals(request.getParameter("supDate"))&&request.getParameter("supDate")!=null){
            if(request.getParameter("supDate").contains("年")){
                supDate = StringUtils.zhDateStrToENDateStr(request.getParameter("supDate"));
            }else{
                supDate = request.getParameter("supDate");
            }
        }
        System.out.println("supId:"+supId+" supName:"+supName+" supCont:"+supCont+" supTel:"+supTel+" supDate:"+supDate);
        SupplierCustom supplierCustom = new SupplierCustom();
        try {
            supplierCustom = supplierService.findSupplierById(supId);
            supplierCustom.setName(supName);
            supplierCustom.setContactName(supCont);
            supplierCustom.setTel(supTel);
            supplierCustom.setContractTime(supDate);
            supplierService.updateSup(supplierCustom);
        }catch (Exception e){
            e.printStackTrace();
            String message = "更新运营商详情时，数据库发生错误，修改信息失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/supplier/upload")
    public void uploadFileAndInsert(@RequestParam("eqXsl") MultipartFile file, HttpServletResponse response) throws Exception{
        if(!file.isEmpty()) {
            supplierService.uploadFile(file);
            SimpleException.sendSuccessMessage(response,objectMapper);
        }
    }
}
