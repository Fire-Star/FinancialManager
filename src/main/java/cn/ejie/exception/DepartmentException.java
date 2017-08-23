package cn.ejie.exception;

/**
 * Created by Administrator on 2017/8/23.
 */
public class DepartmentException extends SimpleException{
    public DepartmentException(String errorType, String errorMessage) {
        super(errorType, errorMessage);
    }

    public DepartmentException() {
    }

    public DepartmentException(String message) {
        super(message);
    }

    public DepartmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentException(Throwable cause) {
        super(cause);
    }
}
