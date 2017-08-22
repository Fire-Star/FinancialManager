package cn.ejie.exception;

/**
 * Created by Administrator on 2017/8/22.
 */

import java.util.Map;

/**
 * 这个类将是以后的错误信息的父类。
 */
public class SimpleException extends GetAllCustomException{

    private String errorType;//这是错误类型，一般是前端会用的一个东西，它标志着 key 。
    private String errorMessage;//这是错误信息，代表具体错误解释。
    private String view;//目标跳转页面

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public SimpleException(String errorType,String errorMessage){
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
    public SimpleException() {
    }

    public SimpleException(String message) {
        super(message);
    }

    public SimpleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleException(Throwable cause) {
        super(cause);
    }

    public static Map<String,String> getMapMessage(Map<String,String> responseJsonMessage,Exception e){
        if(e instanceof SimpleException) {
            SimpleException ex = (SimpleException) e;
            responseJsonMessage.put(ex.getErrorType(), ex.getErrorMessage());
        }else{
            e.printStackTrace();
        }
        return responseJsonMessage;
    }
}
