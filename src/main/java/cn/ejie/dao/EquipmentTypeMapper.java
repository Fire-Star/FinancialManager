package cn.ejie.dao;

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
    public List<EquipmentTypeCustom> selectAllEquipmentType() throws Exception;

    /**
     * 设备类型添加
     * @param equipmentTypeCustom
     * @throws Exception
     */
    public void insertSingleEquipmentType(EquipmentTypeCustom equipmentTypeCustom) throws Exception;
}
