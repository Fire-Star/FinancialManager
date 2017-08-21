package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentTypeMapper;
import cn.ejie.po.EquipmentType;
import cn.ejie.pocustom.EquipmentTypeCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class EquipmentTypeController {
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;

    @RequestMapping("/showAllEquipmentType")
    public @ResponseBody List<EquipmentTypeCustom> showAllEquipmentType(){
        try {
            return equipmentTypeMapper.selectAllEquipmentType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    @RequestMapping("/addEquipmentType")
    public void addEquipmentType(EquipmentTypeCustom equipmentTypeCustom, HttpServletResponse response, HttpServletRequest request){
        System.out.println(request.getCharacterEncoding());
        try {
            System.out.println(equipmentTypeCustom);
            System.out.println(request.getParameter("equipmentTypeName"));
            equipmentTypeMapper.insertSingleEquipmentType(equipmentTypeCustom);
            response.getWriter().println("add success！！！");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().println("add failed！！！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
