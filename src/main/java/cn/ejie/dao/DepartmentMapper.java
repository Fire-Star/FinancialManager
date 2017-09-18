package cn.ejie.dao;

import cn.ejie.pocustom.DepartmentCustom;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface DepartmentMapper {
    /**
     * 查找城市下的所有部门。
     * @param city
     * @return
     * @throws Exception
     */
    public List<DepartmentCustom> findDepartmentByCity(String city) throws Exception;

    /**
     * 插入指定城市下的部门。
     * @param departmentCustom
     * @throws Exception
     */
    public void addDepartment(DepartmentCustom departmentCustom) throws Exception;

    /**
     * 查询是否该城市下的部门存在。
     * @param departmentCustom
     * @return
     * @throws Exception
     */
    public Integer hasDepartment(DepartmentCustom departmentCustom) throws Exception;

    /**
     * 通过部门名称查询部门ID
     * @param params
     * @return
     * @throws Exception
     */
    public String findDepartmentIDByDepNameAndCity(Map<String,String> params) throws Exception;

    /**
     * 通过部门ID查询城市ID
     * @param id
     * @return
     * @throws Exception
     */
    public String findCityIdByDepartmentId(String id) throws Exception;

    /**
     * 查询出所有的 Department
     * @return
     * @throws Exception
     */
    public List<DepartmentCustom> findAllDepartment() throws Exception;

    /**
     * 通过城市ID和部门名称查找出部门ID
     *  params :String cityID , String departStr
     * @return
     * @throws Exception
     */
    public String findDepartIDByCityIDAndDepartStr(Map<String,String> params) throws Exception;

    /**
     *通过id查询部门
     * @param id
     * @return
     * @throws Exception
     */
    public String findDepartmentById(String id) throws Exception;
}
