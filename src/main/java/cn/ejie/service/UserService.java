package cn.ejie.service;

import cn.ejie.dao.UserMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.User;
import cn.ejie.pocustom.UserCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private String errorType = "userErrorType";

    public List<UserCustom> findAll(){
        List<UserCustom> userList = new ArrayList<>();
        try {
            userList = userMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return userList;
    }

    public List<UserCustom> findBySql(String sql){
        List<UserCustom> userList = new ArrayList<UserCustom>();
        try {
            userList = userMapper.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userList;
    }
    public User findUserByUsername(User user) throws Exception{
        if(user.getUsername()==null||"".equals(user.getUsername())){
            throw new SimpleException(errorType,"系统发生错误：查询用户时，用户名不能为空！！！");
        }
        return userMapper.findUserByUsername(user);
    }
    public UserCustom findUserByName(String userName) throws Exception{
        if(userName==null||"".equals(userName)){
            throw new SimpleException(errorType,"系统发生错误：查询用户时，用户名不能为空！！！");
        }
        return userMapper.findUserByName(userName);
    }
    public String findCityByUserName(String userName) throws Exception{
        if(userName==null||"".equals(userName)){
            throw new SimpleException(errorType,"系统发生错误：查询用户时，用户名不能为空！！！");
        }
        return userMapper.findCityByUserName(userName);
    }

    public String findCityIdByUserName(String userName) throws Exception{
        if(userName==null||"".equals(userName)){
            throw new SimpleException(errorType,"系统发生错误：查询用户时，用户名不能为空！！！");
        }
        return userMapper.findCityIdByUserName(userName);
    }

    public void insertUser(UserCustom userCustom) throws Exception{
        try {
            userMapper.insertUser(userCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateUser(UserCustom userCustom) throws Exception{
        try {
            userMapper.updateUser(userCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delUser(String userName) throws Exception{
        try {
            userMapper.delUser(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
