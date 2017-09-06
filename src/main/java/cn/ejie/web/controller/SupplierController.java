package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.service.SupplierService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/supplier/showAll")
    public @ResponseBody List<SupplierCustom> showAllSupplier() throws Exception {
        return  supplierService.findAllSupplier();
    }

    @RequestMapping("/supplier/add")
    public void addSingleSupplier(HttpServletRequest request, HttpServletResponse response){
        SupplierCustom supplierCustom = SimpleBeanUtils.setMapPropertyToBean(SupplierCustom.class,request.getParameterMap());
        System.out.println(supplierCustom);
        try {
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
	 @RequestMapping("/supplier/query")
    public String testSupplierquery(){
        return "/WEB-INF/pages/supplier/supplierquery.html";
    }
    @RequestMapping("/supplier/search")
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
        List<Object> list = new ArrayList<Object>();
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
        }
        int total = 10;
        Map<String,Object> map = new HashMap<String,Object>();
        //map.put("total",total);
        //map.put("rows",list);
        //JSONObject supplierJSON = new JSONObject();
        //supplierJSON = JSONObject.fromObject(map);
        //System.out.println(supplierJSON.toString());
        //SimpleException.sendMessage(response,supplierJSON.toString(),objectMapper);
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
