package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.Staff;
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

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    private String errorType = "errorType";

    public void insertSingleEquipment(EquipmentCustom equipmentCustom) throws Exception{
        System.out.println(equipmentCustom);
        System.out.println(equipmentCustom.getBuyCount());
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentCustom);//验证Bean属性是否为空！！！
        String countStr = equipmentCustom.getBuyCount();

        int len = countStr.length();
        for(int i=0;i<len;i++){
            if(!Character.isDigit(countStr.charAt(i))){
                throw new SimpleException(errorType,"购买数量只能够为整数！");
            }
        }
        Integer iCount = Integer.parseInt(countStr);

        String stateID = equipmentStateMapper.findStateIDByStateName(inserState);//查找ID并设置ID
        equipmentCustom.setEqStateId(stateID);


        //将字符串转换为ID
        String purchaseCityID = cityService.findCityIDByCity(equipmentCustom.getBuyCity());
        String purchaseDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getBuyCity(),equipmentCustom.getPurchasDepart());

        String belongCityID = cityService.findCityIDByCity(equipmentCustom.getCity());
        String belongDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getCity(),equipmentCustom.getBelongDepart());

        equipmentCustom.setBuyCity(purchaseCityID);
        equipmentCustom.setPurchasDepart(purchaseDepartID);

        equipmentCustom.setCity(belongCityID);
        equipmentCustom.setBelongDepart(belongDepartID);


        equipmentCustom.setPurchasTime(StringUtils.zhDateStrToENDateStr(equipmentCustom.getPurchasTime()));//将时间转换并设置

        String cityOtherID = cityService.findCityOtherIDByCityID(belongCityID);//查找出城市ID

        String eqTypeOtherId = equipmentTypeService.findEquipmentTypeOtherIDByTypeName(equipmentCustom.getEqType());//查找出设备类型ID

        int counti = iCount.intValue();

        while (counti-->0){
            Integer count = equipmentMapper.countEquipmentByCity(belongCityID)+1;
            String eqOtherIdAfter = StringUtils.fillPreString(count.toString(),'0',4);//计算出设备ID

            equipmentCustom.setEqOtherId(cityOtherID+eqTypeOtherId+eqOtherIdAfter);
            equipmentMapper.insertSingleEquipment(equipmentCustom);
            System.out.println("插入1");
        }
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

    public void delEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception{
        String eqType = equipmentCustom.getEqType();
        String eqName = equipmentCustom.getEqName();
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备类型不能为空！！！");
        }
        if(eqName == null || eqName.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备名称不能为空！！！");
        }

        equipmentMapper.delEquipmentByEqTypeAndEqName(equipmentCustom);
    }

    public void delEquipmentByEqType(String eqType) throws Exception{
        if(eqType == null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备类型不能为空！！！");
        }
        equipmentMapper.delEquipmentByEqType(eqType);
    }

    public Integer countEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception{
        String eqType = equipmentCustom.getEqType();
        String eqName = equipmentCustom.getEqName();
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备类型不能为空！！！");
        }
        if(eqName == null || eqName.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备名称不能为空！！！");
        }
        Integer count = equipmentMapper.countEquipmentByEqTypeAndEqName(equipmentCustom);
        if(count == null){
            throw new SimpleException("equipmentErrorType","系统数据库发生错误，请通知管理员！！！");
        }
        return count;
    }

    public Integer countEquipmentByEqType(String eqType) throws Exception{
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备类型不能为空！！！");
        }
        Integer count = equipmentMapper.countEquipmentByEqType(eqType);
        if(count == null){
            throw new SimpleException("equipmentErrorType","系统数据库发生错误，请通知管理员！！！");
        }
        return  count;
    }
    public Integer countEquipment(String city) throws Exception{
        if(city==null || city.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，城市不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByCity(city);
        if(count==null){
            throw new SimpleException(errorType,"查询公司设备时，系统发生故障！");
        }
        return count;
    }

    public Integer countEquipmentByDepartment(String department) throws Exception{
        if(department == null || department.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，部门不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByDepartment(department);
        if(count==null){
            throw new SimpleException(errorType,"查询公司设备时，系统发生故障！");
        }
        return count;
    }

    public Integer countAnyEquipmentByCity(String cityID) throws Exception{
        if(cityID == null || cityID.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，公司名称不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByCity(cityID);
        if(count == null){
            throw new SimpleException(errorType,"通过城市查询设备数量时，系统发生错误！！！");
        }
        return count;
    }

    public void updateEquipment(EquipmentCustom equipmentCustom) throws Exception{
        try {
            equipmentMapper.updateEquipment(equipmentCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<EquipmentCustom> findAllKindsEq() throws Exception{
        List<EquipmentCustom> equipmentCustomList = new ArrayList<EquipmentCustom>();
        try {
            equipmentCustomList = equipmentMapper.findAllKindsEq();
        }catch (Exception e){
            e.printStackTrace();
        }
        return equipmentCustomList;
    }
}
