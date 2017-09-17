package cn.ejie.service;

import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.pocustom.EquipmentStateCustom;

import javax.annotation.Resource;

public class EquipmentStateService {
    @Resource
    private EquipmentStateMapper equipmentStateMapper;

    public EquipmentStateCustom searchById(String eqStateID){
        return equipmentStateMapper.selectByPrimaryKey(eqStateID);
    }

    public int insert(EquipmentStateCustom equipmentStateCustom) {
        return equipmentStateMapper.insert(equipmentStateCustom);
    }

    public int update(EquipmentStateCustom equipmentStateCustom) {
        return equipmentStateMapper.updateByPrimaryKey(equipmentStateCustom);
    }

    public int delete(String eqStateID){
        return equipmentStateMapper.deleteByPrimaryKey(eqStateID);
    }

}
