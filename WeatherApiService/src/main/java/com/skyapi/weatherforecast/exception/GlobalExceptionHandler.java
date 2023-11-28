package com.skyapi.weatherforecast.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleGenericException(HttpServletRequest request, Exception ex){
        ErrorDto error = new ErrorDto();
        error.setTimeStamp(new Date());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setPath(request.getServletPath());
        error.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        LOGGER.error(ex.getMessage(), ex);
        return error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ErrorDto error = new ErrorDto();
       error.setTimeStamp(new Date());
       error.setStatus(HttpStatus.BAD_REQUEST.value());
       error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

       List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
       fieldErrors.forEach(fieldError -> {
           error.addError(fieldError.getDefaultMessage());
        });
       return new ResponseEntity<>(error, headers, status);
    }
}
