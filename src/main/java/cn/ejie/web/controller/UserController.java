package cn.ejie.web.controller;

import cn.ejie.annotations.SystemLogAOP;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.VerifyMessage;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.pocustom.UserRoleCustom;
import cn.ejie.service.CityService;
import cn.ejie.service.UserRoleService;
import cn.ejie.service.UserService;
import cn.ejie.service.VertifyCodeService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户类的控制器
 */
@Controller
public class UserController {
    @Resource(name="authenticationManagerw")
    private AuthenticationManager myAuthenticationManager;

    @Autowired
    private VertifyCodeService vertifyCodeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/user/loginPage")
    public String loginPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Map<String,String> message = new HashMap<>();
        if(!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            response.sendRedirect(request.getContextPath()+"/user/index");
        }
        return "/WEB-INF/pages/log.html";
    }
    @RequestMapping(value = "/user/login")
    public void login(HttpServletRequest request,HttpSession session,HttpServletResponse response,UserCustom userCustom){

        Map<String,String> message = new HashMap<>();
        UserCustom user = SimpleBeanUtils.setMapPropertyToBean(UserCustom.class,request.getParameterMap());
        System.out.println("#########################################");
        System.out.println("提交POST时，IP地址为："+request.getRemoteAddr());
        System.out.println(userCustom.getUsername());
        System.out.println(userCustom.getPassword());
        System.out.println(session.getId()+"验证码："+userCustom.getVerifyCode());
        System.out.println(session.getId()+"正确验证码："+(String)session.getAttribute("verifyCode"));
        System.out.println("#########################################\n");
        if(userCustom.getVerifyCode()==null || !userCustom.getVerifyCode().equalsIgnoreCase(((String)session.getAttribute("verifyCode")))) {

            message.clear();
            message.put("code","0");
            message.put("msg","验证码错误！");

            SimpleException.sendMessage(response,message,objectMapper);

            return;
        }
        System.out.println(session.getAttribute("verifyCode"));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userCustom.getUsername(), userCustom.getPassword());
        try {
            Authentication authentication = myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆

            message.clear();
            message.put("code","1");
            message.put("location",request.getContextPath()+"/user/index");

            SimpleException.sendMessage(response,message,objectMapper);

        } catch (AuthenticationException ex) {
            message.clear();
            message.put("code","0");
            message.put("msg","用户名或密码错误！");

            SimpleException.sendMessage(response,message,objectMapper);
        }
    }

    @RequestMapping("/user/virifyImage")
    public void getVerifyImage(HttpSession session,HttpServletResponse response,HttpServletRequest request){

        try {
            response.setHeader("Content-Type","image/gif");
            VerifyMessage verifyMessage = vertifyCodeService.getVerifyMessage();
            System.out.println(session.getId()+"调用了验证码图片方法，请求了图片："+verifyMessage.getVerifyCode());
            ImageIO.write(verifyMessage.getVerifyImage(),"gif",response.getOutputStream());
            session.setAttribute("verifyCode",verifyMessage.getVerifyCode());
            System.out.println(session.getId()+"调用了验证码图片方法="+session.getAttribute("verifyCode"));
            System.out.println("调用了验证码图片方法=IP为："+request.getRemoteAddr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/user/query")
    public String userCheck(){
        return "/WEB-INF/pages/user/userquery.html";
    }

    @RequestMapping("/user/user/search")
    public void userQuery(HttpServletResponse response,HttpServletRequest request){
        System.out.println("账号权限界面table数据加载......");
        List<UserCustom> userList = new ArrayList<UserCustom>();
        String name = "";
        String cityName = "";
        String sql = "";
        String cityId = "";
        String sqltemp = "SELECT username,password,city FROM user";

        if(!request.getParameter("username").equals("") && request.getParameter("username")!=null){
            name = request.getParameter("username");
        }
        if(!request.getParameter("city").equals("") && request.getParameter("city")!=null){
            cityName = request.getParameter("city");
        }
        System.out.println("user:"+name+" city:"+cityName);
        if (!name.equals("")||!cityName.equals("")){
            sqltemp = sqltemp + " WHERE";
            if (!name.equals("")){
                sqltemp = sqltemp + " username like '%"+ name +"%'";
            }
            if(!cityName.equals("")){
                try {
                    cityId = cityService.findCityIDByCity(cityName);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sqltemp = sqltemp + " and city='"+ cityId +"'";
            }
            if(sqltemp.contains("WHERE and")){
                sql = sqltemp.replaceAll("WHERE and","WHERE");
            }else{
                sql = sqltemp;
            }
        }
        System.out.println("sql:"+sql);
        try {
            if(!sql.equals("")){
                userList = userService.findBySql(sql);
            }else {
                userList = userService.findAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Object> list = new ArrayList<>();
        for(int i = 0;i<userList.size();i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("name",userList.get(i).getUsername());
            String city = "";
            try {
                if(!"".equals(userList.get(i).getCity().toString())&&userList.get(i).getCity()!=null){
                    city = cityService.findCityNameByCityID(userList.get(i).getCity().toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("city",city);
            String roleName ="";
            try {
                roleName = userRoleService.findRoleByUserName(userList.get(i).getUsername().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("role",roleName);
            list.add(map);
        }
        JSONArray jsonObject = new JSONArray();
        jsonObject = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }

    @RequestMapping("/user/index")
    public String main(){
        return "/WEB-INF/pages/index.jsp";
    }

    @RequestMapping("/user/getUsername")
    public void getUserName(HttpServletResponse response){
        System.out.println("主界面获取登录名......");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //通过spring security获得登录的用户名
        String userName = userDetails.getUsername();
        Map<String,String> map = new HashMap<String, String>();
        map.put("userName",userName);
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println(jsonObject.toString());
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }

    @RequestMapping("/user/add")
    @SystemLogAOP(module = "帐号查询",methods = "添加帐号")
    public void insertUser(HttpServletResponse response,HttpServletRequest request){
        System.out.println("添加用户...");
        String username = "";
        String cityTemp = "";
        String password = "";
        String city = "";
        String role = "";
        if(!"".equals(request.getParameter("userName"))&&request.getParameter("userName")!=null){
            username = request.getParameter("userName");
        }
        if(!"".equals(request.getParameter("userPass"))&&request.getParameter("userPass")!=null){
            password = request.getParameter("userPass");
        }
        if(!"".equals(request.getParameter("city"))&&request.getParameter("city")!=null){
            cityTemp = request.getParameter("city");
        }
        if(!"".equals(request.getParameter("userRole"))&&request.getParameter("userRole")!=null){
            role = request.getParameter("userRole");
        }
        UserCustom userCustom = new UserCustom();
        UserRoleCustom userRoleCustom = new UserRoleCustom();
        try {
            city = cityService.findCityIDByCity(cityTemp);
        }catch (Exception e){
            e.printStackTrace();
        }
        userRoleCustom.setUserName(username);
        userRoleCustom.setRoleId(role);
        userCustom.setUsername(username);
        userCustom.setPassword(password);
        userCustom.setCity(city);
        UserCustom userCustom1 = null;
        try {
            userCustom1 = userService.findUserByName(username);
        }catch (Exception e){
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        if(userCustom1==null){
            try {
                userService.insertUser(userCustom);
                userRoleService.insertUserRole(userRoleCustom);
            }catch (Exception e){
                Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
                SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                return;
            }
            SimpleException.sendSuccessMessage(response,objectMapper);
        }else{
            String message = "用户名已存在，请修改用户名重新添加！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }

    }
    @RequestMapping("/user/edit")
    @SystemLogAOP(module = "帐号查询",methods = "账号密码修改")
    public void editUser(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("重置用户密码...");
        String username = "";
        String password = "";
        if(!"".equals(request.getParameter("userName"))&&request.getParameter("userName")!=null){
            username = request.getParameter("userName");
        }
        if(!"".equals(request.getParameter("userPass"))&&request.getParameter("userPass")!=null){
            password = request.getParameter("userPass");
        }
        UserCustom userCustom = new UserCustom();
        try {
            userCustom = userService.findUserByName(username);
        }catch (Exception e){
            e.printStackTrace();
            String message = "用户不存在，修改密码失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        userCustom.setPassword(password);
        try {
            userService.updateUser(userCustom);
        }catch (Exception e){
            e.printStackTrace();
            String message = "在修改用户密码时，数据库发生错误，修改密码失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/user/del")
    @SystemLogAOP(module = "帐号查询",methods = "删除帐号")
    public void delUser(HttpServletResponse response,HttpServletRequest request){
        System.out.println("删除用户...");
        String username = "";
        if(!"".equals(request.getParameter("userName"))&&request.getParameter("userName")!=null){
            username = request.getParameter("userName");
        }
        System.out.println("username:"+username);
        try {
            userRoleService.deluserRoleByUserName(username);
            userService.delUser(username);
        }catch (Exception e){
            e.printStackTrace();
            String message = "在删除用户时，数据库发生错误，删除用户失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

}
