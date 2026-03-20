package web.crawler.demo.exception;

public class JSoupClientConnectException extends RuntimeException {

    public JSoupClientConnectException(String message) {
        super(message);
    }

    public JSoupClientConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}