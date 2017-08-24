package cn.ejie.exception;

/**
 * Created by Administrator on 2017/8/24.
 */
public class SupplierException extends SimpleException{
    public SupplierException(String errorType, String errorMessage) {
        super(errorType, errorMessage);
    }

    public SupplierException() {
    }

    public SupplierException(String message) {
        super(message);
    }

    public SupplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupplierException(Throwable cause) {
        super(cause);
    }
}
