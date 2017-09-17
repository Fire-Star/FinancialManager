package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.ext.EUC_CN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class EquipmentService {
    private static final String inserState = "闲置";

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EquipmentStateMapper equipmentStateMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CityService cityService;

    public void insertSingleEquipment(EquipmentCustom equipmentCustom) throws Exception{
        System.out.println(equipmentCustom);
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentCustom);//验证Bean属性是否为空！！！

        String stateID = equipmentStateMapper.findStateIDByStateName(inserState);//查找ID并设置ID
        equipmentCustom.setEqStateId(stateID);

        {
            //将字符串转换为ID
            String purchaseCityID = cityService.findCityIDByCity(equipmentCustom.getBuyCity());
            String purchaseDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getBuyCity(),equipmentCustom.getPurchasDepart());

            String belongCityID = cityService.findCityIDByCity(equipmentCustom.getCity());
            String belongDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getCity(),equipmentCustom.getBelongDepart());

            equipmentCustom.setBuyCity(purchaseCityID);
            equipmentCustom.setPurchasDepart(purchaseDepartID);

            equipmentCustom.setCity(belongCityID);
            equipmentCustom.setBelongDepart(belongDepartID);
        }

        equipmentCustom.setPurchasTime(StringUtils.zhDateStrToENDateStr(equipmentCustom.getPurchasTime()));//将时间转换并设置

        Integer count = equipmentMapper.countEquipment()+1;
        String eqOtherId = StringUtils.fillPreString(count.toString(),'0',4);
        System.out.println(eqOtherId);
        equipmentCustom.setEqOtherId(eqOtherId);
        equipmentMapper.insertSingleEquipment(equipmentCustom);
    }

    public List<EquipmentCustom> findAllBySql(String sql) throws Exception{
        List<EquipmentCustom> list = new ArrayList<EquipmentCustom>();
        try {
            list = equipmentMapper.findAllBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return list;
    }

    public List<EquipmentCustom> findAll() throws Exception{
        List<EquipmentCustom> list = new ArrayList<EquipmentCustom>();
        try{
            list = equipmentMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return list;
    }

    public EquipmentCustom findById(String id) throws Exception{
        EquipmentCustom equipmentCustom = new EquipmentCustom();
        try {
            equipmentCustom = equipmentMapper.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return equipmentCustom;
    }
}
