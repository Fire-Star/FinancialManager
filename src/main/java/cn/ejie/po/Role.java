package cn.ejie.po;

import cn.ejie.annotations.BeanPropertyErrorType;

/**
 * Created by Administrator on 2017/8/9.
 */
public class Role {

    private String rid;
    /**
     * 角色名称，常常是权限名称。
     */
    @BeanPropertyErrorType(propertyName = "角色名称")
    private String rname;
    //权限描述
    @BeanPropertyErrorType(propertyName = "角色描述")
    private String rdescription;

    public Role() {
    }

    public Role(String rname, String rdescription) {

        this.rname = rname;
        this.rdescription = rdescription;
    }

    public Role(String rid, String rname, String rdescription) {

        this.rid = rid;
        this.rname = rname;
        this.rdescription = rdescription;
    }

    public String getRid() {

        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRdescription() {
        return rdescription;
    }

    public void setRdescription(String rdescription) {
        this.rdescription = rdescription;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid='" + rid + '\'' +
                ", rname='" + rname + '\'' +
                ", rdescription='" + rdescription + '\'' +
                '}';
    }
}
