package cn.ejie.service;

import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.pocustom.EquipmentStateCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentStateService {
    @Autowired
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

    public List<EquipmentStateCustom> searchAllEqState(){
        List<EquipmentStateCustom> list = new ArrayList<EquipmentStateCustom>();
        try {
            list = equipmentStateMapper.search();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
