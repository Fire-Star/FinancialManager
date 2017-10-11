package cn.ejie.service;

import cn.ejie.dao.ResRoleMapper;
import cn.ejie.pocustom.ResRoleCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResRoleService {

    @Autowired
    private ResRoleMapper resRoleMapper;

    public String findResRoleIDByResIDAndRoleID(ResRoleCustom resRoleCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(resRoleCustom);
        return resRoleMapper.findResRoleIDByResIDAndRoleID(resRoleCustom);
    }

    public void addResAndRole(ResRoleCustom resRoleCustom) throws Exception{

    }
}
