package cn.ejie.service;

import cn.ejie.dao.UserRoleMapper;
import cn.ejie.pocustom.UserCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public String findRoleByUserName(String username){
        String roleName = "";
        try {
            roleName = userRoleMapper.findRoleByUserName(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return roleName;
    }

}
