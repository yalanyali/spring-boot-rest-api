package rest.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class ResponseEntityException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("############");
        List<String> details = new ArrayList<>();
        details.add(request.getDescription(false));
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), details);
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // General exception handler
    /*@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(request.getDescription(false));
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), details);
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError: fieldErrors) {
            details.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
                details);

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}