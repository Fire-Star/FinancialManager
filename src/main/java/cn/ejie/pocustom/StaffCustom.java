package cn.ejie.pocustom;

import cn.ejie.po.Staff;

public class StaffCustom extends Staff{
    /**
     * 这里写员工字段的扩展属性
     */
    boolean isPiInsert = false;
    private String errorMessage;//错误信息

    public boolean isPiInsert() {
        return isPiInsert;
    }

    public void setPiInsert(boolean piInsert) {
        isPiInsert = piInsert;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return super.toString()+"StaffCustom{" +
                "isPiInsert=" + isPiInsert +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
