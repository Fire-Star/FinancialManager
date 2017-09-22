package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentNameCustom;
import cn.ejie.service.EquipmentNameService;
import cn.ejie.service.EquipmentTypeService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public void insertSingleEquipmentName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String,String> responseJsonMessage = new HashMap<>();

        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());

        equipmentNameService.insertSingleEquipmentName(equipmentNameCustom);

        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/findAllEquipmentNameByEqTypeName")
    public @ResponseBody Map<String,Object> findAllEquipmentNameByEqTypeName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());

        List<EquipmentNameCustom> equipmentNameCustoms = null;

        //这里面出了问题，由全局异常处理器处理！
        equipmentNameCustoms = equipmentNameService.findAllEquipmentNameByEqTypeName(equipmentNameCustom.getEqTypeId());
        Map<String,Object> result = new HashMap<>();
        result.put("data",equipmentNameCustoms);
        result.put("key",equipmentNameCustom.getEqTypeId());
        return result;
    }

    @RequestMapping("/equipmentName/delName")
    public void delEquipmentName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());
        equipmentNameService.delEquipmentName(equipmentNameCustom);
        SimpleException.sendMessage(response,objectMapper,"success","删除成功！！");
    }
}
