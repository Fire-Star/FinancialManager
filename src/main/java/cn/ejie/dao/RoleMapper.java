package cn.ejie.dao;

import cn.ejie.po.Role;
import cn.ejie.pocustom.RoleCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
public interface RoleMapper {
    public List<Role> findAllRoles();

    public String findRoleIDByRname(String rname) throws Exception;

    public void addRole(RoleCustom roleCustom) throws Exception;
}
