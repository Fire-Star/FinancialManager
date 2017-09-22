package cn.ejie.service;

import cn.ejie.dao.EquipmentTypeMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentTypeCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.StringUtils;
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

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentNameService equipmentNameService;

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
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentTypeCustom);//对数据进行是否为空或空字符串校验。

        if(count>0){
            throw new EquipmentException(errorType,"该设备类型已经存在！");
        }

        Integer nowCount = equipmentTypeMapper.findEquipmentTypeCount();
        String eqTypeOherId = StringUtils.fillPreString(nowCount.toString(),'0',2);

        System.out.println(eqTypeOherId);

        equipmentTypeCustom.setEqTypeOtherId(eqTypeOherId);
        equipmentTypeMapper.insertSingleEquipmentType(equipmentTypeCustom);
    }

    public Integer findEquipmentTypeCountByTypeName(String equipmentTypeName) throws Exception {
        return equipmentTypeMapper.findEquipmentTypeCountByTypeName(equipmentTypeName);
    }

    public String findEquipmentTypeIDByTypeName(String equipmentTypeName) throws Exception{
        if(equipmentTypeName==null || equipmentTypeName.equals("")){
            throw new EquipmentException(errorType,"设备类型名称不能为空！");
        }
        String result = equipmentTypeMapper.findEquipmentTypeIDByTypeName(equipmentTypeName);
        if(result == null){
            throw new EquipmentException(errorType,"对不起，你输入的设备类型不存在！");
        }
        return result;
    }

    public String findEquipmentTypeOtherIDByTypeName(String equipmentTypeName) throws Exception{
        return equipmentTypeMapper.findEquipmentTypeOtherIDByTypeName(equipmentTypeName);
    }

    public void delEquipmentType(String eqType) throws Exception{
        Integer count = equipmentService.countEquipmentByEqType(eqType);
        if(count>0){
            throw new SimpleException(errorType,"该设备类型下涉及有设备，不能够删除！！！如有紧急需求，请通知管理员！");
        }
        String eqTypeID = equipmentTypeMapper.findEquipmentTypeIDByTypeName(eqType);
        equipmentNameService.delEquipmentNameByEqType(eqTypeID);//删除所有该类型的设备名称
        equipmentTypeMapper.delEqTypeByEqTypeName(eqType);//删除该设备类型
    }
}
