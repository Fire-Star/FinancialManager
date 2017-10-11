package cn.ejie.dao;

import cn.ejie.po.Res;
import cn.ejie.po.ResRole;
import cn.ejie.po.Role;
import cn.ejie.pocustom.ResRoleCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public interface ResRoleMapper {
    public List<Role> findAllRolesByRes(Res res);

    public String findResRoleIDByResIDAndRoleID(ResRoleCustom resRoleCustom) throws Exception;

    public void addResAndRole(ResRoleCustom resRoleCustom) throws Exception;
}
