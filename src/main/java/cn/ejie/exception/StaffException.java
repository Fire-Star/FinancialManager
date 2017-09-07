package cn.ejie.exception;

public class StaffException extends SimpleException{
    public StaffException(String errorType, String errorMessage) {
        super(errorType, errorMessage);
    }

    public StaffException() {
    }

    public StaffException(String message) {
        super(message);
    }

    public StaffException(String message, Throwable cause) {
        super(message, cause);
    }

    public StaffException(Throwable cause) {
        super(cause);
    }
}
