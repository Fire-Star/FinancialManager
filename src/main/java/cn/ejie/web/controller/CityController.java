package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.service.CityService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/city/add")
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
        //这里的错误由全局异常处理！
        return cityService.findAllCitys();
    }
}
