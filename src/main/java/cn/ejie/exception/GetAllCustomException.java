package cn.ejie.exception;

/**
 * Created by Administrator on 2017/8/22.
 */
public class GetAllCustomException extends  Exception{
    /**
     * 在全站捕获异常的时候用。
     */
    public GetAllCustomException() {
        super();
    }

    public GetAllCustomException(String message) {
        super(message);
    }

    public GetAllCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetAllCustomException(Throwable cause) {
        super(cause);
    }

    protected GetAllCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}