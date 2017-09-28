package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentBorrowMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.Department;
import cn.ejie.pocustom.DepartmentCustom;
import cn.ejie.pocustom.EquipmentBorrowCustom;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EquipmentBorrowController {

    @Autowired
    private EquipmentBorrowService equipmentBorrowService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipmentStateService equipmentStateService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/borrow/serch")
    public void borrowLogSerch(HttpServletRequest request, HttpServletResponse response){
        System.out.println("维修记录查询......");
        String eqId = "";
        if(request.getParameter("equipId") != null){
            eqId = request.getParameter("equipId");
        }
        List<EquipmentBorrowCustom> equipmentBorrowCustomList = new ArrayList<EquipmentBorrowCustom>();
        try {
            equipmentBorrowCustomList = equipmentBorrowService.findBorrowByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < equipmentBorrowCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            //状态名称获取
            String stateName = "";
            if (!"".equals(equipmentBorrowCustomList.get(i).getState())){
                try {
                    stateName = equipmentStateService.findStateNameById(equipmentBorrowCustomList.get(i).getState());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            map.put("status",stateName);
            String userName = "";
            StaffCustom staffCustom = new StaffCustom();
            if (!"".equals(equipmentBorrowCustomList.get(i).getUseBy())) {
                try {
                    staffCustom = staffService.findStaffById(equipmentBorrowCustomList.get(i).getUseBy());
                    userName = staffCustom.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            map.put("userName",userName);
            map.put("userId",equipmentBorrowCustomList.get(i).getUseBy());
            String position = "";
            if(!"".equals(equipmentBorrowCustomList.get(i).getUseByDepart())){
                try {
                    position = departmentService.findDepartmentById(equipmentBorrowCustomList.get(i).getUseByDepart());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            map.put("operatorTime",equipmentBorrowCustomList.get(i).getDoTime());
            map.put("position",position);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println("jsonArray:"+jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }

    /**
     * 通过员工ID查询员工设备
     * @param response
     * @param request
     */
    @RequestMapping("/user/staff/findEqByStaffId")
    public void findEqByStaffId(HttpServletResponse response,HttpServletRequest request){
        System.out.println("用户详情界面，table的数据加载......");
        String staffId = "";
        if(!"".equals(request.getParameter("staffId"))&&request.getParameter("staffId")!=null){
            staffId = request.getParameter("staffId");
        }
        String sql = "";
        List<EquipmentBorrowCustom> equipmentCustomList = new ArrayList<EquipmentBorrowCustom>();
        try {
            if(!"".equals(staffId)){
                equipmentCustomList = equipmentBorrowService.findAllByUserId(staffId);
            }else{
                throw new SimpleException("errorType","查询设备出借记录时，用户Id不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(JSONArray.fromObject(equipmentCustomList).toString());
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < equipmentCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            String eqId = "";
            String eqName = "";
            if(!"".equals(equipmentCustomList.get(i).getEqId())){
                eqId = equipmentCustomList.get(i).getEqId().substring(0,8);
                eqName = equipmentCustomList.get(i).getEqId().substring(8);
            }
            map.put("eqId",equipmentCustomList.get(i).getUseByDepart());
            map.put("eqOtherID",eqId);
            map.put("eqName",eqName);
            map.put("opratorTime",equipmentCustomList.get(i).getDoTime());
            map.put("status",equipmentCustomList.get(i).getState());
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("equipBor/addEqBorLog")
    public void addEqBorLog(HttpServletRequest request,HttpServletResponse response){
        System.out.println("添加借调记录......");
        String eqState = "";
        String eqId = "";
        String doTime = "";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        doTime = format.format(date);
        if(request.getParameter("eqID")!=null&&!"".equals(request.getParameter("eqID"))){
            eqId = request.getParameter("eqID");
        }
        if(request.getParameter("eqState")!=null&&!"".equals(request.getParameter("eqState"))){
            eqState = request.getParameter("eqState");
        }
        String stateId = "";
        try {
            stateId = equipmentStateService.findStateIDByStateName(eqState);
        }catch (Exception e){
            String message = "在查询状态ID时，数据库发生错误！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        if(!"".equals(stateId)){
            EquipmentBorrowCustom equipmentBorrowCustom = new EquipmentBorrowCustom();
            String useByDepart = "";
            String useId = "";
            String borDetail = "";
            String depId = "";
            if(eqState.equals("使用")){
                System.out.println("使用......");
                String eqBorCity = "";
                if(request.getParameter("eqBorCity")!=null&&!"".equals(request.getParameter("eqBorCity"))){
                    eqBorCity = request.getParameter("eqBorCity");
                }
                if(request.getParameter("eqBorBelong")!=null&&!"".equals(request.getParameter("eqBorBelong"))){
                    useByDepart = request.getParameter("eqBorBelong");
                }
                if(request.getParameter("eqBorName")!=null&&!"".equals(request.getParameter("eqBorName"))){
                    useId = request.getParameter("eqBorName");
                }
                if(request.getParameter("eqBorDetail")!=null&&!"".equals(request.getParameter("eqBorDetail"))){
                    borDetail = request.getParameter("eqBorDetail");
                }
                if(!"".equals(eqId)&&!"".equals(eqState)&&!"".equals(eqBorCity)&&!"".equals(useByDepart)&&!"".equals
                        (useId)&&!"".equals(borDetail)){
                    System.out.println("eqId:"+eqId+" eqState:"+eqState+" eqBorCity:"+eqBorCity+" " +
                            "useByDep:"+useByDepart+" useId:"+useId+" borDetail:"+borDetail+" doTime:"+doTime);
                    try {
                        depId = departmentService.findDepartIDByCityStrAndDepartStr(eqBorCity,useByDepart);
                    }catch (Exception e){
                        e.printStackTrace();
                        String message = "在查询部门ID时，数据库发生错误！";
                        SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                        return;
                    }
                }else {
                    String message = "字段不能为空，添加借调记录失败！";
                    SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                    return;
                }
            }else if(eqState.equals("闲置")){
                System.out.println("闲置......");
                String eqBorNoCity = "";
                if(request.getParameter("eqBorNoCity")!=null&&!"".equals(request.getParameter("eqBorNoCity"))){
                    eqBorNoCity = request.getParameter("eqBorNoCity");
                }
                if(request.getParameter("eqBorNoBelong")!=null&&!"".equals(request.getParameter("eqBorNoBelong"))){
                    useByDepart = request.getParameter("eqBorNoBelong");
                }
                if(request.getParameter("eqBorNoDetail")!=null&&!"".equals(request.getParameter("eqBorNoDetail"))){
                    borDetail = request.getParameter("eqBorNoDetail");
                }
                System.out.println("eqId:"+eqId+" eqState:"+eqState+" eqBorCity:"+eqBorNoCity+" " +
                        "useByDep:"+useByDepart+" borDetail:"+borDetail+" doTime:"+doTime);
                try {
                    depId = departmentService.findDepartIDByCityStrAndDepartStr(eqBorNoCity,useByDepart);
                }catch (Exception e){
                    e.printStackTrace();
                    String message = "在查询部门ID时，数据库发生错误！";
                    SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                    return;
                }
            }else if(eqState.equals("报废")){
                System.out.println("报废......");
                if(request.getParameter("eqBorUnDetail")!=null&&!"".equals(request.getParameter("eqBorUnDetail"))){
                    borDetail = request.getParameter("eqBorUnDetail");
                }
                System.out.println("eqId:"+eqId+" eqState:"+eqState+" eqBorCity:"+" " +
                        "useByDep:"+useByDepart+" borDetail:"+borDetail+" doTime:"+doTime);
            }else{
                String message = "在删除用户时，数据库发生错误，删除用户失败！";
                SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                return;
            }
            equipmentBorrowCustom.setEqId(eqId);
            equipmentBorrowCustom.setUseByDepart(depId);
            equipmentBorrowCustom.setUseBy(useId);
            equipmentBorrowCustom.setDoTime(doTime);
            equipmentBorrowCustom.setState(stateId);
            equipmentBorrowCustom.setBorDetail(borDetail);
            System.out.println("equipmentBorrowCustom:"+ JSONObject.fromObject(equipmentBorrowCustom));
            try {
                equipmentBorrowService.insertEqBorrowLog(equipmentBorrowCustom);
                SimpleException.sendMessage(response,"添加借调记录成功！",objectMapper);//报告错误信息到前台！
            }catch (Exception e){
                e.printStackTrace();
                String message = "在删除用户时，数据库发生错误，删除用户失败！";
                SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                return;
            }
            EquipmentCustom equipmentCustom = new EquipmentCustom();
            try {
                equipmentCustom = equipmentService.findById(eqId);
                equipmentCustom.setEqStateId(stateId);
                equipmentService.updateEquipment(equipmentCustom);
            }catch (Exception e){
                e.printStackTrace();
                String message = "在修改设备状态时，数据库发生错误，修改设备记录失败！";
                SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                return;
            }
        }

    }

}
