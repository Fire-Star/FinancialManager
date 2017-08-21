package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.pocustom.EquipmentCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    public void insertSingleEquipment(EquipmentCustom equipmentCustom) throws Exception{
        equipmentMapper.insertSingleEquipment(equipmentCustom);
    }
}
