package pl.pjatk.nbp.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleNbpApiException(Exception ex) {
        // Obsługa błędu zwracanego przez API NBP
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Domyślny kod odpowiedzi HTTP

        if (ex instanceof HttpClientErrorException) {
            httpStatus = (HttpStatus) ((HttpClientErrorException) ex).getStatusCode();
        } else if (ex instanceof HttpServerErrorException) {
            httpStatus = (HttpStatus) ((HttpServerErrorException) ex).getStatusCode();
        }

        // Tworzenie odpowiedzi HTTP z odpowiednim kodem błędu i treścią błędu
        ApiResponse errorResponse = new ApiResponse(httpStatus, errorMessage);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}