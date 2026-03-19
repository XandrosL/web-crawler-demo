package web.crawler.demo.exception;

public class JSoupClientConnectException extends RuntimeException {

    public JSoupClientConnectException() {
    }

    public JSoupClientConnectException(String message) {
        super(message);
    }

    public JSoupClientConnectException(Throwable cause) {
        super(cause);
    }

    public JSoupClientConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSoupClientConnectException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}