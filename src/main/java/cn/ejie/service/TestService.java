package cn.ejie.service;

import cn.ejie.dao.UserMapper;
import cn.ejie.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service
public class TestService {

    @Autowired
    private UserMapper userMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public User findUserByUsername(User user) throws Exception{
        return  userMapper.findUserByUsername(user);
    }
}
