package vn.edu.usth.mcma.backend.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.usth.mcma.backend.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessException> handleBusinessException(BusinessException be) {
        return ResponseEntity.status(Integer.parseInt(be.getStatus())).body(be);
    }
}
