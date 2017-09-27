package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.FixedLogCustom;
import cn.ejie.service.FixedLogService;
import cn.ejie.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
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
public class FixedLogController {

    private ObjectMapper objectMapper;
    @Autowired
    private FixedLogService fixedLogService;

    @RequestMapping("/eqfix/query")
    public void findFixedLog(HttpServletRequest request, HttpServletResponse response){
        String eqId = "";
        if(request.getParameter("equipId") != null){
            eqId = request.getParameter("equipId");
        }
        System.out.println("借出记录查询by eqid:"+eqId);
        List<FixedLogCustom> fixedLogCustomList = new ArrayList<FixedLogCustom>();
        try {
            fixedLogCustomList = fixedLogService.findAllByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(fixedLogCustomList.size());
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < fixedLogCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            map.put("fixTime",fixedLogCustomList.get(i).getFixTime());
            map.put("fixType",fixedLogCustomList.get(i).getFixType());
            map.put("fixDetail",fixedLogCustomList.get(i).getFixDetail());
            list.add(map);
        }

       /* List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 50; i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("fixTime",i+"status");
            map.put("fixType",i+"userName");
            map.put("fixDetail",i+"operatorTime");
            list.add(map);
        }*/
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("/equipFix/addEqFixLog")
    public void addEqFixLog(HttpServletResponse response,HttpServletRequest request){
        System.out.println("添加设备维修信息...");
        String eqId = "";
        String fixTime = "";
        String fixType = "";
        String fixDetail = "";
        if(request.getParameter("eqID") != null&&!"".equals(request.getParameter("eqID"))){
            eqId = request.getParameter("eqID");
        }
        if(request.getParameter("fixTime") != null&&!"".equals(request.getParameter("fixTime"))){
            fixTime =  StringUtils.zhDateStrToENDateStr(request.getParameter("fixTime"));
        }
        if(request.getParameter("fixType") != null&&!"".equals(request.getParameter("fixType"))){
            fixType = request.getParameter("fixType");
        }
        if(request.getParameter("fixDetail") != null&&!"".equals(request.getParameter("fixDetail"))){
            fixDetail = request.getParameter("fixDetail");
        }
        System.out.println("eqId:"+eqId+" fixTime:"+fixTime+" fixType:"+fixType+" fixDetail:"+fixDetail);
        FixedLogCustom fixedLogCustom = new FixedLogCustom();
        fixedLogCustom.setFixTime(fixTime);
        fixedLogCustom.setFixType(fixType);
        fixedLogCustom.setFixDetail(fixDetail);
        fixedLogCustom.setEqId(eqId);
        try {
            fixedLogService.insertFixlog(fixedLogCustom);
        }catch (Exception e){
            e.printStackTrace();
            String message = "保存维修记录时，数据库发生错误，保存记录失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            System.out.println("213");
            return;
        }
        SimpleException.sendMessage(response,"保存维修记录成功！",objectMapper);
    }
}
