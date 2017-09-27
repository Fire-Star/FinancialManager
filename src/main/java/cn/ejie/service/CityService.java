package cn.ejie.service;

import cn.ejie.dao.CityMapper;
import cn.ejie.dao.DepartmentMapper;
import cn.ejie.exception.DepartmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.MaxValue;
import cn.ejie.pocustom.CityCustom;
import cn.ejie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
@Service
public class CityService{

    private String errorType = "cityErrorType";
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private MaxValueService maxValueService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<CityCustom> findAllCitys() throws Exception{
        List<CityCustom> result = null;
        try {
            result = cityMapper.findAllCitys();
        }catch (Exception e){
            e.printStackTrace();
            throw new DepartmentException(errorType,"数据库发生错误！！！");
        }
        return result;
    }

    public void addCity(CityCustom cityCustom) throws Exception{
        String city = cityCustom.getCity();
        if(city == null || city.equals("")){
            throw new DepartmentException(errorType,"添加城市时，城市不能为空！");
        }
        Integer count = cityMapper.hasCity(city);
        if(count>0){
            throw new DepartmentException(errorType,"该城市已经存在！");
        }
        Integer tempCityMax = Integer.parseInt(maxValueService.findValueByKey("cityMax"))+1;
        String cityMax = tempCityMax+"";
        cityMax = StringUtils.fillPreString(cityMax,'0',2);
        cityCustom.setCityOtherID(cityMax);
        System.out.println(cityCustom);
        cityMapper.addCity(cityCustom);
        MaxValue maxValue = new MaxValue();
        maxValue.setKey("cityMax");
        maxValue.setValue(cityMax);
        maxValueService.updataMaxValue(maxValue);
    }

    public String findCityIDByCity(String city) throws Exception{
        if(city == null || city.equals("")){
            throw new DepartmentException(errorType,"城市字符不能为空！");
        }
        String cityID = cityMapper.findCityIDByCity(city);
        if(cityID == null){
            throw new DepartmentException(errorType,"该城市不存在！");
        }
        return cityID;
    }

    public String findCityNameByCityID(String cityID) throws Exception {
        if(cityID==null || cityID.equals("")){
            throw new SimpleException(errorType,"系统发生错误：城市ID不能为空！！！");
        }
        return cityMapper.findCityNameByCityID(cityID);
    }

    public String findCityOtherIDByCityID(String cityID) throws Exception{
        return cityMapper.findCityOtherIDByCityID(cityID);
    }

    public void delCity(String city) throws Exception{
        if(city==null || city.equals("")){
            throw new SimpleException(errorType,"删除城市时，城市字段不能为空！");
        }
        String cityID = findCityIDByCity(city);

        Integer staffCount = staffService.countStaffByCity(cityID);
        Integer equipmentCount = equipmentService.countAnyEquipmentByCity(cityID);
        System.out.println(staffCount+"--->eq="+equipmentCount);
        if(staffCount>0 || equipmentCount>0){
            throw new SimpleException(errorType,"不能够删除该城市，该城市下还存在员工和设备！");
        }
        departmentMapper.delDepartByCity(cityID);
        cityMapper.delCity(cityID);
    }
}
