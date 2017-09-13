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

    @RequestMapping("")
    public void findFixedLog(HttpServletRequest request, HttpServletResponse response){
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 50; i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("","");

            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
}
