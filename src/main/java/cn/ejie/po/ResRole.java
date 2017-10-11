package cn.ejie.po;

import cn.ejie.annotations.BeanPropertyErrorType;

public class ResRole {
    private String res_r_id;//角色资源ID
    @BeanPropertyErrorType(propertyName = "资源ID")
    private String res_id;//资源ID
    @BeanPropertyErrorType(propertyName = "角色ID")
    private String r_id;//角色ID

    public String getRes_r_id() {
        return res_r_id;
    }

    public void setRes_r_id(String res_r_id) {
        this.res_r_id = res_r_id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    @Override
    public String toString() {
        return "ResRole{" +
                "res_r_id='" + res_r_id + '\'' +
                ", res_id='" + res_id + '\'' +
                ", r_id='" + r_id + '\'' +
                '}';
    }
}
