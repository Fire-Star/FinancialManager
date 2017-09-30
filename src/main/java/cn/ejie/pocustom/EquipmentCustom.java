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

    private int x; //插入数据时的坐标x
    private int y; //插入数据时的坐标y
    private String message;//未插入的错误信息

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(String buyCount) {
        this.buyCount = buyCount;
    }

    @Override
    public String toString() {
        return super.toString()+"EquipmentCustom{" +
                "buyCount='" + buyCount + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", message='" + message + '\'' +
                '}';
    }
}
