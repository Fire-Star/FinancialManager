package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentNameMapper;
import cn.ejie.pocustom.EquipmentNameCustom;
import cn.ejie.service.EquipmentNameService;
import cn.ejie.utils.SimpleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class EquipmentNameController {

    @Autowired
    private EquipmentNameService equipmentNameService;

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
    public void insertSingleEquipmentName(HttpServletRequest request){

        EquipmentNameCustom equipmentNameCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentNameCustom.class,request.getParameterMap());

        try {
            equipmentNameService.insertSingleEquipmentName(equipmentNameCustom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
