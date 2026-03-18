package web.crawler.demo.exception;

public class DocumentParserException extends RuntimeException {

    public DocumentParserException() {
    }

    public DocumentParserException(String message) {
        super(message);
    }

    public DocumentParserException(Throwable cause) {
        super(cause);
    }

    public DocumentParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentParserException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}