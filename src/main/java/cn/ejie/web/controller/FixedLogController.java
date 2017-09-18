package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.FixedLogCustom;
import cn.ejie.service.FixedLogService;
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
}
