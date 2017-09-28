package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
public interface EquipmentMapper {
    public void insertSingleEquipment (EquipmentCustom equipmentCustom) throws Exception;

    public Integer countEquipment() throws Exception;

    public List<EquipmentCustom> findAllBySql(String sql) throws Exception;

    public List<EquipmentCustom> findAll() throws  Exception;

    public EquipmentCustom findById(String id) throws Exception;

    public Integer countEquipmentByCity(String cityID) throws Exception;

    public void delEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception;

    public void delEquipmentByEqType(String eqType) throws Exception;

    public Integer countEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception;

    public Integer countEquipmentByEqType(String eqType) throws Exception;

    public Integer countEquipmentByDepartment(String department) throws Exception;

    public Integer countAnyEquipmentByCityID(String cityID) throws Exception;

    public void updateEquipment(EquipmentCustom equipmentCustom) throws Exception;

    public List<EquipmentCustom> findAllKindsEq() throws Exception;

    public List<EquipmentCustom> findAllKindsEqByCityId(String city) throws Exception;

    public Integer countEqForStatis(String eqName,String brand,String cityId) throws Exception;

    public Double sumEqMoneyForStatis(String eqName,String brand,String cityId) throws Exception;

    public Integer countEqForStatisByState(String eqName,String brand,String cityId,String stateId) throws Exception;
}
