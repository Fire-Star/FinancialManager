package cn.ejie.web.controller;

import cn.ejie.annotations.SystemLogAOP;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.User;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.service.CityService;
import cn.ejie.service.UserService;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("/city/add")
    @SystemLogAOP(module = "部门字段管理",methods = "添加城市字段")
    public void addCity(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CityCustom cityCustom = SimpleBeanUtils.setMapPropertyToBean(CityCustom.class,request.getParameterMap());
        Map<String,String> message = new HashMap<>();
        try {
            cityService.addCity(cityCustom);
        }catch (Exception e){
            //这里的错误通常是要通过Json发送到前端！
            SimpleException.getMapMessage(message,e);
            SimpleException.sendMessage(response,message,objectMapper);
            return;
        }
       SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/city/findAllCity")
    public @ResponseBody List<CityCustom> findAllCitys() throws Exception{
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User userParam = new User();
        userParam.setUsername(userName);

        User user = userService.findUserByUsername(userParam);
        if(user.getCity()==null || user.getCity().equals("")){
            return cityService.findAllCitys();
        }
        //这里的错误由全局异常处理！
        List<CityCustom> result = new ArrayList<>();
        CityCustom cityCustom = new CityCustom();
        cityCustom.setCity(cityService.findCityNameByCityID(user.getCity()));
        result.add(cityCustom);
        return result;
    }

    @RequestMapping("/city/del")
    @SystemLogAOP(module = "部门字段管理",methods = "删除城市字段")
    public void delCity(HttpServletRequest request,HttpServletResponse response)throws Exception{
        CityCustom cityCustom = SimpleBeanUtils.setMapPropertyToBean(CityCustom.class,request.getParameterMap());
        System.out.println(cityCustom);
        cityService.delCity(cityCustom.getCity());
        SimpleException.sendMessage(response,objectMapper,"success","删除该城市成功！");
    }
}
