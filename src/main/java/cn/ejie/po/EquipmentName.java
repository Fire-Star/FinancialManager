package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/22.
 */
public class EquipmentName {
    private String eqNameId;//设备名称ID
    private String eqName;//设备名称
    private String eqTypeId;//设备类型

    public EquipmentName() {
    }

    public EquipmentName(String eqNameId, String eqName, String eqTypeId) {
        this.eqNameId = eqNameId;
        this.eqName = eqName;
        this.eqTypeId = eqTypeId;
    }

    @Override
    public String toString() {
        return "EquipmentName{" +
                "eqNameId='" + eqNameId + '\'' +
                ", eqName='" + eqName + '\'' +
                ", eqTypeId='" + eqTypeId + '\'' +
                '}';
    }

    public String getEqNameId() {
        return eqNameId;
    }

    public void setEqNameId(String eqNameId) {
        this.eqNameId = eqNameId;
    }

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public String getEqTypeId() {
        return eqTypeId;
    }

    public void setEqTypeId(String eqTypeId) {
        this.eqTypeId = eqTypeId;
    }
}
