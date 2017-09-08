package cn.ejie.service;

import cn.ejie.dao.UserMapper;
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
}
