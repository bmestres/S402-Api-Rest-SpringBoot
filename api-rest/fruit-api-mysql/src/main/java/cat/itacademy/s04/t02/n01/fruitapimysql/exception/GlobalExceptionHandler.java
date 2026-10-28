package cat.itacademy.s04.t02.n01.fruitapimysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FruitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRFruitNotFoundCase(FruitNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFoundCase(SupplierNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepeatedSupplierNameException.class)
    public ResponseEntity<ErrorResponse> handleExistingSupplierNameCase(RepeatedSupplierNameException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}
