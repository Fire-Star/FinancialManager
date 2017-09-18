package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentBorrowMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentBorrowCustom;
import cn.ejie.service.EquipmentBorrowService;
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
public class EquipmentBorrowController {

    @Autowired
    private EquipmentBorrowService equipmentBorrowService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/borrow/serch")
    public void borrowLogSerch(HttpServletRequest request, HttpServletResponse response){
        String eqId = "";
        if(request.getParameter("equipId") != null){
            eqId = request.getParameter("equipId");
        }
        System.out.println("维修记录查询by eqid:"+eqId);

        List<EquipmentBorrowCustom> equipmentBorrowCustomList = new ArrayList<EquipmentBorrowCustom>();
        try {
            equipmentBorrowCustomList = equipmentBorrowService.findBorrowByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("borrow:::::"+equipmentBorrowCustomList.size()+"       "+JSONArray.fromObject(equipmentBorrowCustomList).toString());
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < equipmentBorrowCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("index",i+"");
            map.put("status",equipmentBorrowCustomList.get(i).getState());
            map.put("userName",equipmentBorrowCustomList.get(i).getUseBy());
            map.put("operatorTime",equipmentBorrowCustomList.get(i).getDoTime());
            map.put("position",equipmentBorrowCustomList.get(i).getUseByDepart());
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }

}
