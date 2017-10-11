package cn.ejie.service;

import cn.ejie.dao.ResMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.ResCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResService {

    @Autowired
    private ResMapper resMapper;

    private String errorType = "errorType";

    public void addRes(ResCustom resCustom) throws Exception {
        BeanPropertyValidateUtils.validateIsEmptyProperty(resCustom);//Bean属性是否为空验证。
        String resID = findResByResName(resCustom.getRes_url());
        if(resID != null){
            throw new SimpleException(errorType,"你要插入的资源路径 "+ resCustom.getRes_url() +" 已经存在！");
        }
        resMapper.addRes(resCustom);
    }

    public String findResByResName(String resUrl) throws Exception{
        if(resUrl == null || resUrl.equals("")){
            throw new SimpleException(errorType,"查询resUrl时，资源路径不能为空！");
        }
        return resMapper.findResByResName(resUrl);
    }
}
