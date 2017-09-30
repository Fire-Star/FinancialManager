package cn.ejie.service;

import cn.ejie.dao.MaxValueMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.MaxValue;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaxValueService {

    private String errorType = "maxValueError";

    @Autowired
    private MaxValueMapper maxValueMapper;

    public String findValueByKey(String key) throws Exception{
        if(key == null || key.equals("")){
            throw new SimpleException(errorType,"系统级错误：查找最大值时，key不能为空！");
        }
        String value = maxValueMapper.findValueByKey(key);
        if(value==null){
            throw new SimpleException(errorType,"系统级错误：查找的最大值字段不存在，请联系管理员！");
        }
        return value;
    }

    public void updataMaxValue(MaxValue maxValue) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(maxValue);
        maxValueMapper.updataMaxValue(maxValue);
    }

    public void insertMaxValue(MaxValue maxValue) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(maxValue);
        maxValueMapper.insertMaxValue(maxValue);
    }
}
