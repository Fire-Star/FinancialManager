package cn.ejie.exception;

/**
 * Created by Administrator on 2017/8/22.
 */

/**
 * 为了 让错误分类，抽象出了 设备异常信息类。后面将会出现同级的类，如 UserException 等。
 */
public class EquipmentException extends SimpleException{

    private String view;//目标跳转页面

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public EquipmentException(String errorType, String errorMessage) {
        super(errorType, errorMessage);
    }

    public EquipmentException() {
    }

    public EquipmentException(String message) {
        super(message);
    }

    public EquipmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EquipmentException(Throwable cause) {
        super(cause);
    }
}
