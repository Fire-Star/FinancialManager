package cn.ejie.po;

import cn.ejie.annotations.BeanPropertyErrorType;

/**
 * Created by Administrator on 2017/8/24.
 */
public class Supplier {

    private String id;//提供商ID
    @BeanPropertyErrorType(value = "supplierNameError",propertyName = "提供商名称")
    private String name;//提供商名称
    //@BeanPropertyErrorType(value = "supplierAdtitudeError",propertyName = "提供商资质")
    private String adtitude;//资质
    @BeanPropertyErrorType(value = "supplierAddressError",propertyName = "提供商地址")
    private String address;//提供商地址
    @BeanPropertyErrorType(value = "supplierContactNameError",propertyName = "提供商联系人")
    private String contactName;//联系人
    @BeanPropertyErrorType(value = "supplierTelError",propertyName = "提供商联系电话")
    private String tel;//联系电话
    @BeanPropertyErrorType(value = "supplierBusinessError",propertyName = "提供商主营业务")
    private String business;//主营业务
    @BeanPropertyErrorType(value = "supplierContractTimeError",propertyName = "提供商签约时间")
    private String contractTime;//签约时间
    private String customMessage;//自定义信息

    public Supplier() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdtitude() {
        return adtitude;
    }

    public void setAdtitude(String adtitude) {
        this.adtitude = adtitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", adtitude='" + adtitude + '\'' +
                ", address='" + address + '\'' +
                ", contactName='" + contactName + '\'' +
                ", tel='" + tel + '\'' +
                ", business='" + business + '\'' +
                ", contractTime='" + contractTime + '\'' +
                ", customMessage='" + customMessage + '\'' +
                '}';
    }
}
