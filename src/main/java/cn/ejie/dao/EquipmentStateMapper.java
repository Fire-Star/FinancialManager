package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentStateCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentStateMapper {
    int deleteByPrimaryKey(@Param(value="eqStateID")String eqStateID);

    int insert(EquipmentStateCustom equipmentStateCustom);

    EquipmentStateCustom selectByPrimaryKey(@Param(value="eqStateID")String eqStateID);

    int updateByPrimaryKey(EquipmentStateCustom equipmentStateCustom);

    List<EquipmentStateCustom> search();

    String findStateIDByStateName(String state);

    String findStateNameById(String id);
}
