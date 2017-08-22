package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentNameCustom;
import cn.ejie.pocustom.EquipmentTypeCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
public interface EquipmentTypeMapper {
    /**
     * 所有设备类型查询
     * @return
     * @throws Exception
     */
    public List<EquipmentTypeCustom> showAllEquipmentType() throws Exception;

    /**
     * 设备类型添加
     * @param equipmentTypeCustom
     * @throws Exception
     */
    public void insertSingleEquipmentType(EquipmentTypeCustom equipmentTypeCustom) throws Exception;

    /**
     * 通过名称查找设备类型数量
     * @param equipmentTypeName
     * @return
     * @throws Exception
     */
    public Integer findEquipmentTypeCountByTypeName(String equipmentTypeName) throws Exception;

    /**
     * 通过名称查找设备类型ID
     * @param equipmentTypeName
     * @return
     * @throws Exception
     */
    public String findEquipmentTypeIDByTypeName(String equipmentTypeName) throws Exception;
}
