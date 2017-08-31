package cn.ejie.web.controller;

import cn.ejie.po.VerifyMessage;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.VertifyCodeService;
import cn.ejie.utils.SimpleBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户类的控制器
 */
@Controller
public class UserController {
    @Resource(name="authenticationManagerw")
    private AuthenticationManager myAuthenticationManager;

    @Autowired
    private VertifyCodeService vertifyCodeService;

    @RequestMapping("/user/login")
    public @ResponseBody String login(HttpServletRequest request,HttpSession session){
        UserCustom user = SimpleBeanUtils.setMapPropertyToBean(UserCustom.class,request.getParameterMap());
        if(!user.getVerifyCode().equalsIgnoreCase(((String)session.getAttribute("verifyCode")))) {
            return "验证码错误！";
        }
        System.out.println(session.getAttribute("verifyCode"));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication authentication = myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
            return "登录成功！";
        } catch (AuthenticationException ex) {
            return "用户名或密码错误！";
        }
    }

    @RequestMapping("/user/virifyImage")
    public void getVerifyImage(HttpSession session,HttpServletResponse response){
        try {
            VerifyMessage verifyMessage = vertifyCodeService.getVerifyMessage();
            ImageIO.write(verifyMessage.getVerifyImage(),"gif",response.getOutputStream());
            session.setAttribute("verifyCode",verifyMessage.getVerifyCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
