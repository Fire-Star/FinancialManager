package cn.ejie.service;

import cn.ejie.dao.EquipmentTypeMapper;
import cn.ejie.pocustom.EquipmentTypeCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class EquipmentTypeService {
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;

    /**
     * 查询所有设备类型
     * @return
     * @throws Exception
     */
    public List<EquipmentTypeCustom> selectAllEquipmentType() throws Exception{
       return equipmentTypeMapper.selectAllEquipmentType();
    }

    /**
     * 添加单个设备类型
     * @param equipmentTypeCustom
     * @throws Exception
     */
    public void insertSingleEquipmentType(EquipmentTypeCustom equipmentTypeCustom) throws Exception{
        equipmentTypeMapper.insertSingleEquipmentType(equipmentTypeCustom);
    }
}
