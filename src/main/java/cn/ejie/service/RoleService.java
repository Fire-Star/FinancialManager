package cn.ejie.service;

import cn.ejie.dao.RoleMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.RoleCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    private String errorType = "errorType";

    public String findRoleIDByRname(String rname) throws Exception{
        if(rname == null || rname.equals("")){
            throw new SimpleException(errorType,"查询Role角色ID时，Role 的名称不能为空！");
        }
        return roleMapper.findRoleIDByRname(rname);
    }

    public void addRole(RoleCustom roleCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(roleCustom);
        String roleID = findRoleIDByRname(roleCustom.getRname());
        if(roleID != null){
            throw new SimpleException(errorType,"你要添加的角色已经存在！");
        }
        roleMapper.addRole(roleCustom);
    }
}
