package cn.ejie.service;

import cn.ejie.dao.EquipmentTypeMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.pocustom.EquipmentTypeCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class EquipmentTypeService {
    private String errorType = "eqTypeError";

    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;

    /**
     * 查询所有设备类型
     * @return
     * @throws Exception
     */
    public List<EquipmentTypeCustom> showAllEquipmentType() throws Exception{
       return equipmentTypeMapper.showAllEquipmentType();
    }

    /**
     * 添加单个设备类型
     * @param equipmentTypeCustom
     * @throws Exception
     */
    public void insertSingleEquipmentType(EquipmentTypeCustom equipmentTypeCustom) throws Exception{
        int count = findEquipmentTypeCountByTypeName(equipmentTypeCustom.getEquipmentTypeName());
        if(equipmentTypeCustom.getEquipmentTypeName().equals("")){
            throw new EquipmentException(errorType,"设备类型字段不能为空！");
        }
        if(count>0){
            throw new EquipmentException(errorType,"该设备类型已经存在！");
        }
        equipmentTypeMapper.insertSingleEquipmentType(equipmentTypeCustom);
    }

    public Integer findEquipmentTypeCountByTypeName(String equipmentTypeName) throws Exception {
        return equipmentTypeMapper.findEquipmentTypeCountByTypeName(equipmentTypeName);
    }

    public String findEquipmentTypeIDByTypeName(String equipmentTypeName) throws Exception{
        return equipmentTypeMapper.findEquipmentTypeIDByTypeName(equipmentTypeName);
    }
}
