package ua.omniway.dao.exceptions;

public class OmnitrackerException extends Exception {

    public OmnitrackerException() {
    }

    public OmnitrackerException(String message) {
        super(message);
    }

    public OmnitrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OmnitrackerException(Throwable cause) {
        super(cause);
    }

    public OmnitrackerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
