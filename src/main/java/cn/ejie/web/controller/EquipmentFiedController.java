package cn.ejie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EquipmentFiedController {
    /**
     * 系统字段维护
     */

    @RequestMapping("/equipment/fieldManagerPage")
    public String equipmentFieldManager(){
        return "/WEB-INF/pages/systemFieldManager/equipmentFieldManager.html";
    }
}
