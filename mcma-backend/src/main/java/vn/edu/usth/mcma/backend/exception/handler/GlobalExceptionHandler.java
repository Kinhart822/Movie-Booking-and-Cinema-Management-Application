package vn.edu.usth.mcma.backend.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.usth.mcma.backend.exception.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessException> handleBusinessException(BusinessException be) {
        log.error("BusinessException occurred: {}", be.getMessage(), be);
        return ResponseEntity.status(Integer.parseInt(be.getStatus())).body(be);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        log.error("Unknown exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(500).body(e);
    }
}
