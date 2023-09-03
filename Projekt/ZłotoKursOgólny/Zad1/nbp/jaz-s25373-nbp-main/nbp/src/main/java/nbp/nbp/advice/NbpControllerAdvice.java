package nbp.nbp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.net.ConnectException;

@RestControllerAdvice
public class NbpControllerAdvice {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> errorHandler(ResponseStatusException ex){
        HttpStatusCode statusCode = ex.getStatusCode();
        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - Not Found upsi daisy"); // dodałam mniej oczywiste przypisy by podczas sprawdzania błędów można było zauważyć iż są to moje zapiski a nie automatyczne
        } else if (HttpStatus.BAD_REQUEST.equals(statusCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request KURZA TWARZ");
        }

        return ResponseEntity.status(statusCode).body(ex.toString());
    }
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> errorHandlerConnect(ResponseStatusException ex){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Internal Server Error - niestety");
    }
}
