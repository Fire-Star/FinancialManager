package cn.ejie.po;

import cn.ejie.annotations.BeanPropertyErrorType;

/**
 * Created by Administrator on 2017/8/10.
 */
public class Res {
    /**
     * 这个是资源类
     */
    private String res_id;
    @BeanPropertyErrorType(propertyName = "资源链接地址")
    private String res_url;
    @BeanPropertyErrorType(propertyName = "资源链接地址权限描述")
    private String res_description;

    public Res() {
    }

    @Override
    public String toString() {
        return "Res{" +
                "res_id='" + res_id + '\'' +
                ", res_url='" + res_url + '\'' +
                ", res_description='" + res_description + '\'' +
                '}';
    }

    public Res(String res_id, String res_url, String res_description) {

        this.res_id = res_id;
        this.res_url = res_url;
        this.res_description = res_description;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_url() {
        return res_url;
    }

    public void setRes_url(String res_url) {
        this.res_url = res_url;
    }

    public String getRes_description() {
        return res_description;
    }

    public void setRes_description(String res_description) {
        this.res_description = res_description;
    }
}
