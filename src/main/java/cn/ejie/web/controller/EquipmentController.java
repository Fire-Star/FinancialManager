package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.DepartmentCustom;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.pocustom.EquipmentStateCustom;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.*;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipmentStateService equipmentStateService;

    @Autowired
    private FixedLogService fixedLogService;

    @RequestMapping("/equipment/add")
    public void insertSingleEquipment(EquipmentCustom equipmentCustom, HttpServletResponse response){

        try {
            equipmentService.insertSingleEquipment(equipmentCustom);
            SimpleException.sendSuccessMessage(response,objectMapper);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     *
     * @return 设备查询界面
     */
    @RequestMapping("/user/equipment/query")
    public String queryEquip(){
        return "/WEB-INF/pages/equipment/equipquery.html";
    }

    /**
     * 设备查询界面Table数据
     * @param respose
     * @param request
     */
    @RequestMapping("/user/equipment/search")
    public void searchEquipment(HttpServletResponse respose, HttpServletRequest request){
        System.out.println("equipment/search");
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

        System.out.println("userDetail::::"+role+"&&&&&&&&&&"+city);

        //EquipmentCustom equipmentCustomTest = SimpleBeanUtils.setMapPropertyToBean(EquipmentCustom.class,request.getParameterMap());
        String eqID = "";
        String eqType = "";
        String eqName = "";
        String supplier = "";
        String belongDepart = "";
        String eqState = "";
        String time = "";
        if(request.getParameter("eqID") != null){
            eqID = request.getParameter("eqID");
        }
        if(request.getParameter("eqType") != null){
            eqType = request.getParameter("eqType");
        }
        if(request.getParameter("eqName") != null){
            eqName = request.getParameter("eqName");
        }
        if(request.getParameter("supplier") != null){
            supplier = request.getParameter("supplier");
        }
        if(request.getParameter("belongDepart") != null){
            belongDepart = request.getParameter("belongDepart");
        }
        if(request.getParameter("eqState") != null){
            eqState = request.getParameter("eqState");
        }
        if (request.getParameter("time") != null && !"".equals(request.getParameter("time"))){
            time = StringUtils.zhDateStrToENDateStr(request.getParameter("time"));
        }
        System.out.println("eqID:"+eqID+" eqType:"+eqType+" eqName:"+eqName+" supplier:"+supplier+" belongDepart:"+belongDepart+" eqState:"+eqState+" time:"+time);
        String sql = "";
        String sqltemp = "SELECT eq_id as eqId,eq_type as eqType,eq_name as eqName,brand_name as brandName,purchas_depart as purchasDepart,belong_depart as belongDepart,purchas_date as purchasTime,supplier as supplier,eq_state as eqStateId,purchas_price as purchasPrice,custom_message as customMessage,eq_other_id as eqOtherId,city as city FROM equipment";
        if(!eqID.equals("")||!eqType.equals("")||!eqName.equals("")||!supplier.equals("")||!belongDepart.equals("")||!eqState.equals("")||!time.equals("")){
            if("ROLE_ADMIN".equals(role)||"".equals(city)) {
                sqltemp = sqltemp + " WHERE";
            }else{
                sqltemp = sqltemp + " WHERE city='"+  city +"'";
            }
            if(!eqID.equals("")){
                sqltemp = sqltemp + " and eq_other_id='"+eqID+"'";
            }
            if(!eqType.equals("")){
                sqltemp = sqltemp + " and eq_type='"+eqType+"'";
            }
            if(!eqName.equals("")){
                sqltemp = sqltemp + " and eq_name='"+eqName+"'";
            }
            if(!supplier.equals("")){
                sqltemp = sqltemp + " and supplier='"+supplier+"'";
            }
            if(!belongDepart.equals("")){
                sqltemp = sqltemp + " and belong_depart='"+belongDepart+"'";
            }
            if(!eqState.equals("")){
                sqltemp = sqltemp + " and eq_state='"+eqState+"'";
            }
            if(!time.equals("")){
                sqltemp = sqltemp + " and purchas_date='"+time+"'";
            }
            if(sqltemp.contains("WHERE and")){
                sql = sqltemp.replaceAll("WHERE and","WHERE");
            }else{
                sql = sqltemp;
            }
        }else {
            sql = sqltemp;
        }
        System.out.println("sql:"+sql);
        List<EquipmentCustom> listequip = new ArrayList<EquipmentCustom>();
        try {
            listequip = equipmentService.findAllBySql(sql);
        } catch (Exception e) {
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(respose,message,objectMapper);//报告错误信息到前台！
            return;
        }
        System.out.println(JSONArray.fromObject(listequip).toString());

        List<Object> list = new ArrayList<Object>();
        for (int i = 0;i<listequip.size();i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("id",listequip.get(i).getEqId());
            map.put("eqId",listequip.get(i).getEqOtherId());
            map.put("eqType",listequip.get(i).getEqType());
            map.put("eqName",listequip.get(i).getEqName());
            map.put("brandName",listequip.get(i).getBrandName());
            map.put("supplier",listequip.get(i).getSupplier());
            String depart = "";
            try{
                depart = departmentService.findDepartmentById(listequip.get(i).getBelongDepart().toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
            map.put("belongDepart",depart);
            EquipmentStateCustom stateCustom = null;
            try {
                stateCustom = equipmentStateService.searchById(listequip.get(i).getEqStateId());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("eqState",stateCustom.getState());
            map.put("dPurchasPrice",listequip.get(i).getPurchasPrice());
            String month = "";
            try {
                month = StringUtils.getMonthSpace(listequip.get(i).getPurchasTime()) + "";
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("time",month);
            int num = 0;
            try {
                num = fixedLogService.countByEqId(listequip.get(i).getEqId());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("fixnum",num+"");
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(respose,jsonArray.toString(), objectMapper);
    }

    /**
     * 通过员工ID查询员工设备
     * @param response
     * @param request
     */
    @RequestMapping("/user/staff/findEqByStaffId")
    public void findEqByStaffId(HttpServletResponse response,HttpServletRequest request){
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 50; i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            map.put("eqID","eqID"+i);
            map.put("eqName","eqName"+i);
            map.put("opratorTime","opratorTime"+i);
            map.put("status","status"+i);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }

    /**
     *
     * @return 设备详情界面
     */
    @RequestMapping("/user/equip/detail")
    public String findEquipDetailById(){
        return "/WEB-INF/pages/equipment/equipdetail.html";
    }

    @RequestMapping("/equipment/addPage")
    public String equipAdd(){
        return "/WEB-INF/pages/equipment/equipAdd.html";
    }

    @RequestMapping("/user/equip/findEquipByEquipID")
    public void findEquipByEquipID(HttpServletResponse response,HttpServletRequest request){
        String eqId = "";
        if(request.getParameter("equipId") != null){
            eqId = request.getParameter("equipId");
        }
        EquipmentCustom equipmentCustom = new EquipmentCustom();
        try {
            equipmentCustom = equipmentService.findById(eqId);
        }catch (Exception e){
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.fromObject(equipmentCustom);
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }
}
