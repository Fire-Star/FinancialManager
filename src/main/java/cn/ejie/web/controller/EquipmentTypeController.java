package cn.ejie.web.controller;

import cn.ejie.dao.EquipmentTypeMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.po.EquipmentType;
import cn.ejie.pocustom.EquipmentTypeCustom;
import cn.ejie.service.EquipmentTypeService;
import cn.ejie.utils.SimpleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class EquipmentTypeController {
    private String errorType="EquipmentTypeError";

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @RequestMapping("/showAllEquipmentType")
    public @ResponseBody List<EquipmentTypeCustom> showAllEquipmentType() throws Exception{
        try {
            return equipmentTypeService.showAllEquipmentType();
        } catch (Exception e) {
            throw new EquipmentException(errorType,"数据库出错，请稍后重试！");
        }
    }
    @RequestMapping("/addEquipmentType")
    public void addEquipmentType(HttpServletResponse response, HttpServletRequest request) throws IntrospectionException {

        //通过参数绑定会乱码，所以通过 BeanUtils 来参数绑定。
        EquipmentTypeCustom equipmentTypeCustom = SimpleBeanUtils.setMapPropertyToBean(EquipmentTypeCustom.class,request.getParameterMap());

        try {
            equipmentTypeService.insertSingleEquipmentType(equipmentTypeCustom);
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

    public @ResponseBody Integer findEquipmentTypeCountByTypeName(HttpServletRequest request){
        EquipmentTypeCustom equipmentTypeCustom = SimpleBeanUtils.
                setMapPropertyToBean(EquipmentTypeCustom.class,request.getParameterMap());
        String eqTypeName = equipmentTypeCustom.getEquipmentTypeName();
        Integer count = null;
        try {
            count = equipmentTypeService.findEquipmentTypeCountByTypeName(eqTypeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
