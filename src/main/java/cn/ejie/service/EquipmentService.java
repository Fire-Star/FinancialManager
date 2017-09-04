package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.StringUtils;
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
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentCustom);//验证Bean属性是否为空！！！

        Integer count = equipmentMapper.countEquipment();
        String eqOtherId = StringUtils.fillPreString(count.toString(),'0',4);
        System.out.println(eqOtherId);
        equipmentMapper.insertSingleEquipment(equipmentCustom);
    }
}
