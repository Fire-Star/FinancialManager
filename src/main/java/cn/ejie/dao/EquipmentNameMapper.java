package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentNameCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
public interface EquipmentNameMapper {
    /**
     * 插入单个设备名称
     * @param equipmentNameCustom
     * @throws Exception
     */
    public void insertSingleEquipmentName(EquipmentNameCustom equipmentNameCustom) throws Exception;

    /**
     * 查询出所有的设备名称
     * @return
     * @throws Exception
     */
    public List<EquipmentNameCustom> showAllEquipmentName() throws Exception;

    /**
     * 查找相同设备名称和设备类型的UUID的数量
     * @param equipmentNameCustom
     * @return
     * @throws Exception
     */
    public Integer findEquipmentNameCountByEquipmentNameAndType(EquipmentNameCustom equipmentNameCustom) throws Exception;

    public List<EquipmentNameCustom> findAllEquipmentNameByEqTypeID(String eqTypeId) throws Exception;
}
