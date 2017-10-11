package cn.ejie.web.controller;

import cn.ejie.pocustom.ResCustom;
import cn.ejie.service.ResService;
import cn.ejie.utils.SimpleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ResController {
    @Autowired
    private ResService resService;

    @RequestMapping("/res/add")
    private void addRes(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResCustom resCustom = SimpleBeanUtils.setMapPropertyToBean(ResCustom.class,request.getParameterMap());
        resService.addRes(resCustom);
    }
}
