package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.service.CityService;
import cn.ejie.service.DepartmentService;
import cn.ejie.service.EquipmentBorrowService;
import cn.ejie.service.StaffService;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;

import cn.ejie.utils.SimpleBeanUtils;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StaffController {

    @Autowired
    private ObjectMapper objectMapper;
    private String errorType="staffError";

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CityService cityService;

    @Autowired
    private EquipmentBorrowService equipmentBorrowService;

    @RequestMapping("/staff/add")
    public void staffAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
        StaffCustom staffCustom = SimpleBeanUtils.setMapPropertyToBean(StaffCustom.class,request.getParameterMap());
        staffService.addSingleStaff(staffCustom);
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/staff/addPage")
    public String staffAddPage(){
        return "/WEB-INF/pages/staff/staffAdd.html";
    }

    @RequestMapping("/user/staff/query")
    public String search(){
        return "/WEB-INF/pages/staff/staffquery.html";
    }

    @RequestMapping("/user/staff/search")
    public void queryStaff(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String sql = "";
        String name = "";
        String dep = "";
        String position = "";
        String tel = "";
        String entrytime = "";
        String sqltemp = "SELECT id as staffId,name,department as dep,position,tel,entry_time as entryTime,custom_message as customMessages,city FROM staff";
        if(request.getParameter("name")!= null){
            name = request.getParameter("name");
        }
        if(request.getParameter("department")!= null){
            dep = request.getParameter("department");
        }
        if(request.getParameter("position")!= null){
            position = request.getParameter("position");
        }
        if(request.getParameter("tel")!= null){
            tel = request.getParameter("tel");
        }
        if(request.getParameter("entrytime")!= null){
            entrytime = request.getParameter("entrytime");
        }

        if(!name.equals("")||!dep.equals("")||!position.equals("")||!tel.equals("")||!entrytime.equals("")){
            sqltemp = sqltemp + " WHERE";
            if(!name.equals("")){
                sqltemp = sqltemp + " name='"+name+"'";
            }
            if(!dep.equals("")){
                sqltemp = sqltemp + " and department='"+dep+"'";
            }
            if(!position.equals("")){
                sqltemp = sqltemp + " and position='"+position+"'";
            }
            if(!tel.equals("")){
                sqltemp = sqltemp + " and tel='"+tel+"'";
            }
            if(!entrytime.equals("")){
                sqltemp = sqltemp + " and entry_time='"+entrytime+"'";
            }
            if(sqltemp.contains("WHERE and")){
                sql = sqltemp.replaceAll("WHERE and","WHERE");
            }else{
                sql = sqltemp;
            }
        }else{
            sql = sqltemp;
        }
        System.out.println("sql:"+sql);

        List<StaffCustom> staffCustomList = new ArrayList<StaffCustom>();
        try {
            staffCustomList = staffService.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }

        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < staffCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("id",staffCustomList.get(i).getStaffId());
            map.put("name",staffCustomList.get(i).getName());

            String cityName = "";
            try {
                cityName = cityService.findCityNameByCityID(staffCustomList.get(i).getCity().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("city",cityName);
            String depart = "";
            try {
                depart = departmentService.findDepartNameByDepId(staffCustomList.get(i).getDep().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("department",depart);
            map.put("position",staffCustomList.get(i).getPosition());
            map.put("tel",staffCustomList.get(i).getTel());
            map.put("entrytime",staffCustomList.get(i).getEntryTime());
            String borrowNum = "";
            try {
                borrowNum = String.valueOf(equipmentBorrowService.countAllByUser(staffCustomList.get(i).getStaffId()));
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("equipnum",borrowNum);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("/user/staff/detail")
    public String searchStaffDetail(){
        return "/WEB-INF/pages/staff/staffdetail.html";
    }
    @RequestMapping("/user/staff/findStaffByStaffID")
    public void findStaffByStaffID (HttpServletRequest request,HttpServletResponse response) throws Exception{
        System.out.println("yuangongxiangqing");
        String staffId = "";
        StaffCustom staffCustom = new StaffCustom();
        if(request.getParameter("suppId")!=null){
            staffId = request.getParameter("suppId");
        }
        System.out.println("staffId:"+staffId);
        try {
            staffCustom = staffService.findStaffById(staffId);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.fromObject(staffCustom);
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }
}
