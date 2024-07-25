package com.alura.forum.config;

import com.alura.forum.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException exc){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exc.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        exc.getBindingResult().getFieldErrors().forEach(error ->
                responseBody.put(error.getField(), error.getDefaultMessage())
        );
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exc.getMessage());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    // Handle exceptions when the argument passed in the controller URL is not valid
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exc){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Invalid parameter: " + exc.getValue());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("parameter", exc.getName());
        responseBody.put("timestamp", currentTime());
        responseBody.put("requiredType", Objects.requireNonNull(exc.getRequiredType()).getSimpleName());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    private String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
