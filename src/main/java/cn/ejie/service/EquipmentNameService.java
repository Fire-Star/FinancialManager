package cn.ejie.service;

import cn.ejie.dao.EquipmentNameMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.pocustom.EquipmentNameCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
public class EquipmentNameService {
    //每个Service都有一个errorType
    private String errorType ="eqNameError";

    @Autowired
    private EquipmentNameMapper equipmentNameMapper;

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    /**
     * 插入单个设备名称！
     * 业务逻辑：判断设备类型这个字段不为空、并且该设备类型在数据库存在！！！
     * @param equipmentNameCustom
     * @throws Exception
     */
    public void insertSingleEquipmentName(EquipmentNameCustom equipmentNameCustom) throws Exception{

        String eqType = equipmentNameCustom.getEqTypeId();//这个Type目前从前端获取过来还是汉字字符本身。
        if (equipmentNameCustom.getEqName() == null || equipmentNameCustom.getEqName().equals("")) {
            throw new EquipmentException(errorType,"设备名称不能为空！");
        }
        if(eqType == null || eqType.equals("")){
            throw new EquipmentException(errorType,"设备类型不能为空！");
        }
        String eqTypeID = equipmentTypeService.findEquipmentTypeIDByTypeName(eqType);
        if (eqTypeID == null) {
            throw new EquipmentException(errorType,equipmentNameCustom.getEqTypeId()+" 这个设备类型不存在！");
        }
        //必须把汉字字符替换成数据库里面对应的UUID
        equipmentNameCustom.setEqTypeId(eqTypeID);
        
        //判断当前设备类型下的设备名称是否存在。
        Integer count = findEquipmentNameCountByEquipmentNameAndType(equipmentNameCustom);
        if(count>0){
            throw new EquipmentException(errorType,eqType+" 下的 "+equipmentNameCustom.getEqName() +" 已经存在！");
        }

        equipmentNameMapper.insertSingleEquipmentName(equipmentNameCustom);
    }

    /**
     * 查询出所有的设备名称
     * @return
     * @throws Exception
     */
    public List<EquipmentNameCustom> showAllEquipmentName() throws Exception{
        return equipmentNameMapper.showAllEquipmentName();
    }

    public Integer findEquipmentNameCountByEquipmentNameAndType(EquipmentNameCustom equipmentNameCustom) throws Exception {

        String equipmentTypeID = equipmentNameCustom.getEqTypeId();
        String equipmentName = equipmentNameCustom.getEqName();

        if(equipmentTypeID==null || equipmentTypeID.equals("")){
            throw new EquipmentException(errorType,"设备类型不能为空！");
        }
        if(equipmentName==null || equipmentName.equals("")){
            throw new EquipmentException(errorType,"设备名称不能为空！");
        }
        return equipmentNameMapper.findEquipmentNameCountByEquipmentNameAndType(equipmentNameCustom);
    }

    public List<EquipmentNameCustom> findAllEquipmentNameByEqTypeID(String eqTypeId) throws Exception{
        if(eqTypeId==null || eqTypeId.equals("")){
            throw new EquipmentException(errorType,"设备类型不能为空！");
        }
        return  equipmentNameMapper.findAllEquipmentNameByEqTypeID(eqTypeId);
    }
}
