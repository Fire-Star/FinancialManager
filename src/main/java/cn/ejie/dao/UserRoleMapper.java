package cn.ejie.dao;

import cn.ejie.po.Role;
import cn.ejie.pocustom.UserCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
public interface UserRoleMapper {
    public List<Role> findRolesByUsername(UserCustom userCustom);

    public String findRoleByUserName(String userCustom);
}
