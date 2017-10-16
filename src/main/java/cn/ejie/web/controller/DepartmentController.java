package cn.ejie.web.controller;

import cn.ejie.annotations.SystemLogAOP;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.Department;
import cn.ejie.pocustom.DepartmentCustom;
import cn.ejie.service.DepartmentService;
import cn.ejie.utils.SimpleBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
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
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/department/add")
    @SystemLogAOP(module = "部门字段管理",methods = "添加部门字段")
    public void addDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DepartmentCustom departmentCustom = SimpleBeanUtils.setMapPropertyToBean(DepartmentCustom.class,request.getParameterMap());

        //这里面出了问题，由全局异常处理器处理！
        departmentService.addDepartment(departmentCustom);
        //如果没有出问题，那么久添加成功了！
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/department/findDepartment")
    public @ResponseBody List<DepartmentCustom> findDepartmentByCity(HttpServletRequest request) throws Exception{
        DepartmentCustom departmentCustom = SimpleBeanUtils.setMapPropertyToBean(DepartmentCustom.class,request.getParameterMap());
        return departmentService.findDepartmentByCity(departmentCustom.getCity());
    }

    /**
     * 查找出所有的 Department
     * @return
     * @throws Exception
     */
    @RequestMapping("/department/showAll")
    public @ResponseBody List<DepartmentCustom> findAllDepartment() throws Exception{
        return departmentService.findAllDepartment();
    }

    @RequestMapping("/department/showAllByCity")
    public @ResponseBody Map<String,Object> findAllDepartmentByCity(HttpServletRequest request) throws Exception{
        DepartmentCustom departmentCustom = SimpleBeanUtils.setMapPropertyToBean(DepartmentCustom.class,request.getParameterMap());
        List<DepartmentCustom> value = departmentService.findDepartmentByCity(departmentCustom.getCity());
        String key = departmentCustom.getCity();
        Map<String,Object> result = new HashMap<>();
        result.put("key",key);
        result.put("data",value);
        return result;
    }

    @RequestMapping("/department/systemFieldManager")
    public String systemDepartmentManager(){
        return "/WEB-INF/pages/systemFieldManager/departmentFieldManager.html";
    }

    @RequestMapping("/department/del")
    @SystemLogAOP(module = "部门字段管理",methods = "删除部门字段")
    public void delDepartByDepartmentID(HttpServletRequest request,HttpServletResponse response) throws Exception{
         DepartmentCustom departmentCustom =  SimpleBeanUtils.setMapPropertyToBean(DepartmentCustom.class,request.getParameterMap());
         departmentService.delDepartByDepartment(departmentCustom);
         SimpleException.sendMessage(response,objectMapper,"success","删除成功！");
    }
}
