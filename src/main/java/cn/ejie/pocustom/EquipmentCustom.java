package cn.ejie.pocustom;

import cn.ejie.annotations.BeanPropertyErrorType;
import cn.ejie.po.Equipment;

/**
 * Created by Administrator on 2017/8/21.
 */
public class EquipmentCustom extends Equipment{
    /**
     * 这里面写扩展属性
     */
    private String buyCount;//购买数量

    public String getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(String buyCount) {
        this.buyCount = buyCount;
    }
}
