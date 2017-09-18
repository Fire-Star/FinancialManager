package cn.ejie.web.controller;

import cn.ejie.pocustom.EquipmentStateCustom;
import cn.ejie.service.EquipmentStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EquipmentStateController {

    @Autowired
    private EquipmentStateService equipmentStateService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/equipState/findAll")
    public @ResponseBody List<EquipmentStateCustom> findAllEqState() throws Exception{
        return equipmentStateService.searchAllEqState();
    }
}
