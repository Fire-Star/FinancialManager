package cn.ejie.dao;

import cn.ejie.pocustom.CityCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface CityMapper {
    /**
     * 查询出所有的城市
     * @return
     * @throws Exception
     */
    public List<CityCustom> findAllCitys() throws Exception;

    /**
     * 添加城市
     * @param cityCustom
     * @throws Exception
     */
    public void addCity(CityCustom cityCustom) throws Exception;

    /**
     * 查询数据库里面，该城市是否存在
     * @param city
     * @return
     * @throws Exception
     */
    public Integer hasCity(String city) throws Exception;

    /**
     * 通过城市名称查询出该城市ID
     * @param city
     * @return
     * @throws Exception
     */
    public String findCityIDByCity(String city) throws Exception;
}
