package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/21.
 */
public class EquipmentType {
    private String equipmentTypeId;//设备类型ID
    private String equipmentTypeName;//设备类型，这是字符串，不是UUID。
    private String eqTypeOtherId;//设备别名ID

    public String getEqTypeOtherId() {
        return eqTypeOtherId;
    }

    public void setEqTypeOtherId(String eqTypeOtherId) {
        this.eqTypeOtherId = eqTypeOtherId;
    }

    @Override
    public String toString() {
        return "EquipmentType{" +
                "equipmentTypeId='" + equipmentTypeId + '\'' +
                ", equipmentTypeName='" + equipmentTypeName + '\'' +
                ", eqTypeOtherId='" + eqTypeOtherId + '\'' +
                '}';
    }

    public EquipmentType() {
    }


    public EquipmentType(String equipmentTypeId, String equipmentTypeName) {
        this.equipmentTypeId = equipmentTypeId;
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(String equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }
}
