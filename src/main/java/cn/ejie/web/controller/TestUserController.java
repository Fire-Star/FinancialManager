package cn.ejie.web.controller;

import cn.ejie.dao.*;
import cn.ejie.po.Res;
import cn.ejie.po.Role;
import cn.ejie.po.User;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
@Controller
public class TestUserController {
    /**
     * 该类用来测试数据库的相关访问。
     */

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ResMapper resMapper;

    @RequestMapping("/test/showAllUser")
    public void showAllUser(HttpServletResponse response){
        UserCustom userCustom = new UserCustom("MoonFollow","");
        User user = testService.findUserByUsername(userCustom);
        try {
            response.getWriter().println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/test/showAllRoles")
    public void showAllRoles(HttpServletResponse response){
        //设置响应的编码以及响应内容编码。
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //向页面输出内容。
        List<Role> roles = roleMapper.findAllRoles();
        for (Role role : roles) {
            try {
                response.getWriter().println(role.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/test/showUserRolesByUsername")
    public void showUserRolesByUsername(HttpServletResponse response,UserCustom userCustom){
        //设置响应的编码以及响应内容编码。
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Role> roles = userRoleMapper.findRolesByUsername(userCustom);

        if (roles != null && roles.size()==0) {
            try {
                response.getWriter().println("你还没有对 "+userCustom.getUsername()+" 分配权限！！！");
                return ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Role role : roles) {
            try {
                response.getWriter().println(userCustom.getUsername()+"<---->"+role.getRname());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/test/showAllRes")
    public void showAllRes(HttpServletResponse response){
        //设置响应的编码以及响应内容编码。
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //向页面输出内容。
        List<Res> res = resMapper.findAllRes();
        for (Res resI : res) {
            try {
                response.getWriter().println(resI.toString());
                response.getWriter().println("<br>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    private ResRoleMapper resRoleMapper;

    @RequestMapping("/test/showRolesByRes")
    public void showRolesByRes(HttpServletResponse response,Res res){
        //设置响应的编码以及响应内容编码。
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(res==null || (res!=null && res.getRes_url()==null)){
            try {
                response.getWriter().println("亲爱的 Joy ，你是不是犯傻了，你都还没有告诉我资源名称呢。");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        List<Role> roles = resRoleMapper.findAllRolesByRes(res);

        if (roles == null||(roles!=null && roles.size()==0)) {
            try {
                response.getWriter().println("亲爱的 Joy ，我的记忆中还没有关于该 "+res.getRes_url()+" 资源的信息呢。");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        for (Role role : roles) {
            try {
                response.getWriter().println(role.getRname()+"<br>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/user/index")
    public String showMainPage(){
        return "/WEB-INF/pages/main.html";
    }
}
