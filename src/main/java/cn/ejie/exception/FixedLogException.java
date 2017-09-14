package cn.ejie.exception;

public class FixedLogException extends SimpleException{
    public FixedLogException(String errorType, String errorMessage) {
        super(errorType, errorMessage);
    }
}
