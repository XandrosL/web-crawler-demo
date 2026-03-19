package web.crawler.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@SuppressWarnings("null")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JSoupClientConnectException.class)
    public ResponseEntity<Object> handleDocumentParserException(JSoupClientConnectException ex, WebRequest webRequest) {
        log.error(ex.getLocalizedMessage(), ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.BAD_GATEWAY,
                ex.getLocalizedMessage(), null, null, webRequest);
        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, HttpStatus.BAD_GATEWAY, webRequest);

    }

}