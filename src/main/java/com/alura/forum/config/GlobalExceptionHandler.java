package com.alura.forum.config;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.exception.responses.GenericErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
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
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        exc.getBindingResult().getFieldErrors().forEach(error ->
                responseBody.put(error.getField(), error.getDefaultMessage())
        );
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    private String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
