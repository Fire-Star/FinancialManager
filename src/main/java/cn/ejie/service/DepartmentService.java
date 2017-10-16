package cn.ejie.service;

import cn.ejie.dao.DepartmentMapper;
import cn.ejie.exception.DepartmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.pocustom.DepartmentCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
@Service
public class DepartmentService {
    private String errorType = "errorType";

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private StaffService staffService;

    /**
     * 通过城市名称查询部门
     * @param city
     * @return
     * @throws Exception
     */
    public List<DepartmentCustom> findDepartmentByCity(String city) throws Exception{
        if (city == null || city.equals("")) {
            throw new DepartmentException(errorType,"查询部门时，城市不能为空！");
        }
        String cityID = cityService.findCityIDByCity(city);
        //当我们返回的是 List 对象的时候，如果对象里面没有数据，那么不会返回空指针，而是 List 里面没有数据。
        return departmentMapper.findDepartmentByCity(cityID);
    }

    public String findDepartIDByCityStrAndDepartStr(String cityStr , String departStr) throws Exception {
        if(cityStr==null || cityStr.equals("")){
            throw new SimpleException(errorType,"城市不能为空！");
        }
        if(departStr==null || departStr.equals("")){
            throw new SimpleException(errorType,"部门不能为空！");
        }
        String departID = null;
        try {
            String cityID = cityService.findCityIDByCity(cityStr);
            Map<String,String> params = new HashMap<>();
            params.put("cityID",cityID);
            params.put("departStr",departStr);
            departID = departmentMapper.findDepartIDByCityIDAndDepartStr(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SimpleException(errorType,"查找城市ID发生错误！");
        }
        if(departID == null){
            throw new SimpleException(errorType,"该城市下的部门不存在！");
        }

        return departID;
    }

    /**
     * 添加城市下的部门
     * @param departmentCustom
     * @throws Exception
     */
    public void addDepartment(DepartmentCustom departmentCustom) throws Exception{
        String city = departmentCustom.getCity();
        String department = departmentCustom.getDepartment();

        if(city == null || city.equals("")){
            throw new DepartmentException(errorType,"城市不能为空！");
        }
        if(department==null || department.equals("")){
            throw new DepartmentException(errorType,"部门不能为空！");
        }

        String cityID = cityService.findCityIDByCity(city);
        departmentCustom.setCity(cityID);

        Integer count = departmentMapper.hasDepartment(departmentCustom);
        if(count>0){
            throw new DepartmentException(errorType,"当前部门已经存在！"+city+"--->"+department);
        }
        departmentMapper.addDepartment(departmentCustom);
    }

    /**
     * 查询出所有的部门
     * @return
     * @throws Exception
     */
    public List<DepartmentCustom> findAllDepartment() throws Exception{
        List<DepartmentCustom> result = null;

        try {
            result = departmentMapper.findAllDepartment();
        }catch (Exception e){
            throw new SimpleException(errorType,"查询所有部门时，数据库发生错误，请报告给维修人员！");
        }
        return result;
    }

    public String findDepartmentById(String id) throws Exception{
        String departmentCustom = "";
        try {
            departmentCustom = departmentMapper.findDepartmentById(id);
        }catch (Exception e){
            throw new SimpleException(errorType,"查询部门时，数据库发生错误，请报告给维修人员！");
        }
        return departmentCustom;
    }

    public String findDepartNameByDepId(String id) throws Exception{
        String department = "";
        try {
            department = departmentMapper.findDepartNameByDepId(id);
        }catch (Exception e){
            throw new SimpleException(errorType,"查询部门时，数据库发生错误，请报告给维修人员！");
        }
        return department;
    }

    public void delDepartByDepartment(DepartmentCustom depObject) throws Exception{
        String departStr = depObject.getDepartment();
        String cityStr = depObject.getCity();

        String department = findDepartIDByCityStrAndDepartStr(cityStr,departStr);

        if(department==null || department.equals("")){
            throw new SimpleException(errorType,"删除城市部门时，部门字段不能为空！");
        }
        Integer eqConut = equipmentService.countEquipmentByDepartment(department);
        Integer staffCount = staffService.countStaffByDepart(department);
        if(eqConut > 0 || staffCount > 0){
            throw new SimpleException(errorType,"该部门中存在员工或者设备，不能够删除！");
        }
        departmentMapper.delDepartmentByDepartID(department);
    }
}
