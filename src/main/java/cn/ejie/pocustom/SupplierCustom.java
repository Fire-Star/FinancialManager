package cn.ejie.pocustom;

import cn.ejie.po.Supplier;

/**
 * Created by Administrator on 2017/8/24.
 */
public class SupplierCustom extends Supplier{
    /**
     * 这里面写自定义信息
     */
    private String errroMessage;//插入到数据库时，错误信息，打印excel会用到

    private boolean isPiDao = false;

    public boolean isPiDao() {
        return isPiDao;
    }

    public void setPiDao(boolean piDao) {
        isPiDao = piDao;
    }

    public String getErrroMessage() {
        return errroMessage;
    }

    public void setErrroMessage(String errroMessage) {
        this.errroMessage = errroMessage;
    }
}
