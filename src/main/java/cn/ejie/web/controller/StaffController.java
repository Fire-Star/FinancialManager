package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.service.StaffService;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;

import cn.ejie.utils.SimpleBeanUtils;

import org.apache.ibatis.jdbc.Null;
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

    private ObjectMapper objectMapper;

    @Autowired
    private StaffService staffService;

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

    @RequestMapping("/staff/query")
    public String search(){
        return "/WEB-INF/pages/staff/staffquery.html";
    }

    @RequestMapping("/staff/search")
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
            throw new SimpleException("","");
        }

        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < staffCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("name",staffCustomList.get(i).getName());
            map.put("city",staffCustomList.get(i).getCity());
            map.put("department",staffCustomList.get(i).getDep());
            map.put("position",staffCustomList.get(i).getPosition());
            map.put("tel",staffCustomList.get(i).getTel());
            map.put("entrytime",staffCustomList.get(i).getEntryTime());
            map.put("equipnum","equipnum"+i);
            list.add(map);
        }

        /*List<Object> list = new ArrayList<Object>();
        for(int i=0;i<50;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("name","name"+i);
            map.put("city","city"+i);
            map.put("department","department"+i);
            map.put("position","position"+i);
            map.put("tel","tel"+i);
            map.put("entrytime","entrytime"+i);
            map.put("equipnum","equipnum"+i);
            list.add(map);
        }*/
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
