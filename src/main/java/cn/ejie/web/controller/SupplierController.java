package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.po.User;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.service.CityService;
import cn.ejie.service.SupplierService;
import cn.ejie.service.UserService;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        /*List<Object> list = new ArrayList<Object>();
        for(int i=0;i<50;i++){
            Map<String, Object> mapp = new HashMap<String, Object>();
            mapp.put("index", i+"");
            mapp.put("name","test"+i);
            mapp.put("contactName","name"+i);
            mapp.put("tel","1387878454"+i);
            mapp.put("business","busy"+i);
            mapp.put("count","151545");
            mapp.put("money","21321");
            list.add(mapp);
        }*/
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("/user/supplier/findEquipBySuppId")
    public void findEquipBySuppId(HttpServletRequest request,HttpServletResponse response){
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 50; i++) {
            Map map = new HashMap();
            map.put("index",""+i);
            map.put("equipID","equipID"+i);
            map.put("equipType","equipType"+i);
            map.put("equipName","equipName"+i);
            map.put("brand","brand"+i);
            map.put("purchasDepart","purchasDepart"+i);
            map.put("belongDepart","belongDepart"+i);
            map.put("state","state"+i);
            map.put("purchasDate","purchasDate"+i);
            map.put("purchasPrice","purchasPrice"+i);
            map.put("fixTime","fixTime"+i);
            map.put("useTime","useTime"+i);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
