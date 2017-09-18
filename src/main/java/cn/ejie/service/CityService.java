package cn.ejie.service;

import cn.ejie.dao.CityMapper;
import cn.ejie.exception.DepartmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.CityCustom;
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
        cityMapper.addCity(cityCustom);
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
        System.out.println(cityID);
        return cityMapper.findCityNameByCityID(cityID);
    }

    public String findCityOtherIDByCityID(String cityID) throws Exception{
        return cityMapper.findCityOtherIDByCityID(cityID);
    }
}
