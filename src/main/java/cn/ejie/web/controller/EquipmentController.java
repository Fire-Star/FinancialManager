package cn.ejie.web.controller;

import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/addEquipment")
    public void insertSingleEquipment(EquipmentCustom equipmentCustom, HttpServletResponse response){
        System.out.println(equipmentCustom);
        try {
            equipmentService.insertSingleEquipment(equipmentCustom);
            response.getWriter().println("insert Success！！！");
        } catch (Exception e) {
            try {
                response.getWriter().println("insert failed");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }
}
