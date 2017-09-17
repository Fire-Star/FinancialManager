package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/21.
 */

import cn.ejie.annotations.BeanPropertyErrorType;

import java.util.Date;

/**
 * 设备基本信息
 */
public class Equipment {
    private String eqId;//设备ID
    @BeanPropertyErrorType(propertyName = "设备类型")
    private String eqType;//设备类型
    @BeanPropertyErrorType(propertyName = "设备名称")
    private String eqName;//设备名称
    @BeanPropertyErrorType(propertyName = "品牌名称")
    private String brandName;//品牌名称
    @BeanPropertyErrorType(propertyName = "采购部门")
    private String purchasDepart;//采购部门
    @BeanPropertyErrorType(propertyName = "归属部门")
    private String belongDepart;//归属部门
    @BeanPropertyErrorType(propertyName = "采购时间")
    private String purchasTime;//采购时间
    @BeanPropertyErrorType(propertyName = "供应商")
    private String supplier;//供应商
    private String eqStateId;//设备状态
    @BeanPropertyErrorType(propertyName = "采购价格")
    private String purchasPrice;//采购价格
    private String customMessage;//自定义信息，该自定义信息满足以下格式：key:value,key:value
    private String eqOtherId;//设备别名ID
    private String city;//城市ID,归属城市
    private String buyCity;//采购城市

    public String getBuyCity() {
        return buyCity;
    }

    public void setBuyCity(String buyCity) {
        this.buyCity = buyCity;
    }

    public Equipment() {
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "eqId='" + eqId + '\'' +
                ", eqType='" + eqType + '\'' +
                ", eqName='" + eqName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", purchasDepart='" + purchasDepart + '\'' +
                ", belongDepart='" + belongDepart + '\'' +
                ", purchasTime='" + purchasTime + '\'' +
                ", supplier='" + supplier + '\'' +
                ", eqStateId='" + eqStateId + '\'' +
                ", purchasPrice='" + purchasPrice + '\'' +
                ", customMessage='" + customMessage + '\'' +
                ", eqOtherId='" + eqOtherId + '\'' +
                ", city='" + city + '\'' +
                ", buyCity='" + buyCity + '\'' +
                '}';
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    public String getEqType() {
        return eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPurchasDepart() {
        return purchasDepart;
    }

    public void setPurchasDepart(String purchasDepart) {
        this.purchasDepart = purchasDepart;
    }

    public String getBelongDepart() {
        return belongDepart;
    }

    public void setBelongDepart(String belongDepart) {
        this.belongDepart = belongDepart;
    }

    public String getPurchasTime() {
        return purchasTime;
    }

    public void setPurchasTime(String purchasTime) {
        this.purchasTime = purchasTime;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getEqStateId() {
        return eqStateId;
    }

    public void setEqStateId(String eqStateId) {
        this.eqStateId = eqStateId;
    }

    public String getPurchasPrice() {
        return purchasPrice;
    }

    public void setPurchasPrice(String purchasPrice) {
        this.purchasPrice = purchasPrice;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getEqOtherId() {
        return eqOtherId;
    }

    public void setEqOtherId(String eqOtherId) {
        this.eqOtherId = eqOtherId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
