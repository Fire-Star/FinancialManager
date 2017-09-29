package cn.ejie.service;

import cn.ejie.dao.UserRoleMapper;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.pocustom.UserRoleCustom;
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

    public void insertUserRole(UserRoleCustom userRoleCustom)throws Exception{
        try {
            userRoleMapper.insertUserRole(userRoleCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deluserRoleByUserName(String userName) throws Exception{
        try {
            userRoleMapper.deluserRoleByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
