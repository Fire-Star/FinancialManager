package cn.ejie.dao;

import cn.ejie.pocustom.StaffCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffMapper {
    /**
     * 员工删除
     * @param staffId
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(@Param(value="staffId")String staffId) throws Exception;

    /**
     * 员工插入
     * @param staffCustom
     * @return
     * @throws Exception
     */
    int insert(StaffCustom staffCustom) throws Exception;

    /**
     * 通过员工主键查询员工
     * @param staffId
     * @return
     * @throws Exception
     */
    StaffCustom selectByPrimaryKey(@Param(value="staffId")String staffId) throws Exception;

    /**
     * 通过主键修改员工
     * @param staffCustom
     * @return
     * @throws Exception
     */
    int updateByPrimaryKey(StaffCustom staffCustom) throws Exception;

    /**
     * 查询所有员工
     * @return
     * @throws Exception
     */
    List<StaffCustom> search() throws Exception;

    /**
     * 通过 员工所在部门 电话号码 姓名 查询员工数量
     * @param staffCustom
     * @return
     * @throws Exception
     */
    public Integer findStaffCountByDepAndNameAndTel(StaffCustom staffCustom) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    public List<StaffCustom> findAll() throws Exception;

    /**
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<StaffCustom> findBySql(String sql) throws Exception;

    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public StaffCustom findStaffById(String id) throws Exception;
}
