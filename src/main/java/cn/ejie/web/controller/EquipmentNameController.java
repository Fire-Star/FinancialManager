package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentNameMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentNameCustom;
import cn.ejie.service.EquipmentNameService;
import cn.ejie.service.EquipmentTypeService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class EquipmentNameController {

    @Autowired
    private EquipmentNameService equipmentNameService;

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping("/showAllEquipmentName")
    public @ResponseBody List<EquipmentNameCustom> showAllEquipmentName(){
        List<EquipmentNameCustom> result = null;
        try {
            result = equipmentNameService.showAllEquipmentName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/addSingleEquipmentName")
    public @ResponseBody Map<String,String> insertSingleEquipmentName(HttpServletRequest request){
        Map<String,String> responseJsonMessage = new HashMap<>();

        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());

        try {
            equipmentNameService.insertSingleEquipmentName(equipmentNameCustom);
        } catch (Exception e) {
           return SimpleException.getMapMessage(responseJsonMessage,e);
        }
        //如果没有抛出异常，那么这里就是成功返回了！
        responseJsonMessage.put("status","成功插入！！");
        return responseJsonMessage;
    }

    @RequestMapping("/findAllEquipmentNameByEqTypeName")
    public @ResponseBody List<EquipmentNameCustom> findAllEquipmentNameByEqTypeName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());

        List<EquipmentNameCustom> equipmentNameCustoms = null;
        try {
            equipmentNameCustoms = equipmentNameService.findAllEquipmentNameByEqTypeName(equipmentNameCustom.getEqTypeId());
        } catch (Exception e) {
            SimpleException.setView("/WEB-INF/pages/error/normalError.jsp",e);
            throw e;
        }
        return equipmentNameCustoms;
    }
}
