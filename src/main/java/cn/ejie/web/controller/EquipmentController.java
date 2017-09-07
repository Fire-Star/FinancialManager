package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/addEquipment")
    public void insertSingleEquipment(EquipmentCustom equipmentCustom, HttpServletResponse response){
        System.out.println(equipmentCustom);
        try {
            equipmentService.insertSingleEquipment(equipmentCustom);
            response.getWriter().println("insert Success！！！");
        } catch (Exception e) {
            try {
                response.getWriter().println("insert failed");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }
    @RequestMapping("/equipment/equipquery")
    public String queryEquip(){
        return "/WEB-INF/pages/equipment/equipquery.html";
    }
    @RequestMapping("equipment/search")
    public void searchEquipment(HttpServletResponse respose, HttpServletRequest request){
        System.out.println("equipment/search");
        Object object = (Object)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(object.toString());
                //SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
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
        if (request.getParameter("time") != null){
            time = request.getParameter("time");
        }
        System.out.println("eqID:"+eqID+" eqType:"+eqType+" eqName:"+eqName+" supplier:"+supplier+" belongDepart:"+belongDepart+" eqState:"+eqState+" time:"+time);
        String sql = "";
        String sqltemp = "SELECT eq_id,eq_type,eq_name,brand_name,purchas_depart,belong_depart,purchas_date,supplier,eq_state,purchas_price,custom_message,eq_other_id,city FROM equipment";
        if(!eqID.equals("")||!eqType.equals("")||!eqName.equals("")||!supplier.equals("")||!belongDepart.equals("")||!eqState.equals("")||!time.equals("")){
            sqltemp = sqltemp + " WHERE";
            if(!eqID.equals("")){
                sql = sqltemp + " eq_id="+eqID;
            }
            if(!eqType.equals("")){
                sql = sqltemp + " eq_type="+eqType;
            }
            if(!eqName.equals("")){
                sql = sqltemp + " eq_name="+eqName;
            }
            if(!supplier.equals("")){
                sql = sqltemp + " supplier="+supplier;
            }
            if(!belongDepart.equals("")){
                sql = sqltemp + " belong_depart"+belongDepart;
            }
            if(!eqState.equals("")){
                sql = sqltemp + " eq_state="+eqState;
            }
            if(!time.equals("")){
                sql = sqltemp + " purchas_date="+time;
            }
            if(sqltemp.contains("WHERE and")){
                sql = sqltemp.replaceAll("WHERE and","WHERE");
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
        System.out.println(listequip.size());

        List<Object> list = new ArrayList<Object>();
        for (int i = 0;i<listequip.size();i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("eqId",listequip.get(i).getEqOtherId());
            map.put("eqType",listequip.get(i).getEqType());
            map.put("eqName",listequip.get(i).getEqName());
            map.put("brandName",listequip.get(i).getBrandName());
            map.put("supplier",listequip.get(i).getSupplier());
            map.put("belongDepart",listequip.get(i).getBelongDepart());
            map.put("eqState",listequip.get(i).getEqStateId());
            map.put("dPurchasPrice",listequip.get(i).getdPurchasPrice()+"");
            map.put("time","使用时间"+i);
            map.put("fixnum","维修记录"+i);
            list.add(map);
        }
        /*List<Object> lists = new ArrayList<Object>();
        for(int i = 0;i<50;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("eqId","设备ID"+i);
            map.put("eqType","设备类型"+i);
            map.put("eqName","设备名称"+i);
            map.put("brandName","品牌"+i);
            map.put("supplier","供应商"+i);
            map.put("belongDepart","归属部门"+i);
            map.put("eqState","状态"+i);
            map.put("dPurchasPrice","采购价格"+i);
            map.put("time","使用时间"+i);
            map.put("fixnum","维修记录"+i);
            lists.add(map);
        }*/
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(respose,jsonArray.toString(), objectMapper);
    }
}
