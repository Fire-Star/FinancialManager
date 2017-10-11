package cn.ejie.web.controller;

import cn.ejie.pocustom.RoleCustom;
import cn.ejie.service.RoleService;
import cn.ejie.utils.SimpleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role/add")
    public void addRole(HttpServletRequest request, HttpServletResponse response) throws Exception{
        RoleCustom roleCustom = SimpleBeanUtils.setMapPropertyToBean(RoleCustom.class,request.getParameterMap());
        roleService.addRole(roleCustom);
    }
}
