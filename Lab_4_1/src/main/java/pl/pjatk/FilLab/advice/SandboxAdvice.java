package pl.pjatk.FilLab.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SandboxAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExeception(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Exception occured on request. Exception message: " + ex.getLocalizedMessage());
    }
}
