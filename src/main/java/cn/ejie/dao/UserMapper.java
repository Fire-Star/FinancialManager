package cn.ejie.dao;

import cn.ejie.po.User;

/**
 * Created by Administrator on 2017/8/9.
 */
public interface UserMapper {
    public User findUserByUsername(User user);
}
