package cn.ejie.web.controller;

import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.service.SupplierService;
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

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/supplier/showAll")
    public @ResponseBody List<SupplierCustom> showAllSupplier() throws Exception {
        return  supplierService.findAllSupplier();
    }

    @RequestMapping("/supplier/add")
    public void addSingleSupplier(HttpServletRequest request, HttpServletResponse response){
        SupplierCustom supplierCustom = SimpleBeanUtils.setMapPropertyToBean(SupplierCustom.class,request.getParameterMap());

        try {
            supplierService.addSingleSupplier(supplierCustom);
        } catch (Exception e) {
            Map<String,String> message = SimpleException.getMapMessage(new HashMap<>(),e);
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }
}
