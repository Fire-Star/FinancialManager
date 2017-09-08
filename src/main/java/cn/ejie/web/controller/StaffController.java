package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.service.StaffService;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;

import cn.ejie.utils.SimpleBeanUtils;

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
    public void queryStaff(HttpServletRequest request, HttpServletResponse response){
        List<Object> list = new ArrayList<Object>();
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
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
