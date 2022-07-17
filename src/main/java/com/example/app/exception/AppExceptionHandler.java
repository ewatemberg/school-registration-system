package com.example.app.exception;

import com.example.app.enums.ErrorType;
import com.example.app.utils.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception e, HttpServletRequest request) {
        log.error("Generic exception message: {}, cause: {}", e.getLocalizedMessage(), e);
        e.printStackTrace();
        return buildResponse(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getClass());
    }

    private ResponseEntity<Object> buildResponse(String generalApiErrorDetail, HttpStatus status, Class<?> clazz) {
        ApiError apiError = new ApiError(ErrorType.BUSINESS, generalApiErrorDetail);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handlerNotFoundException(NotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        ApiError apiError = ex.getError();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxCapacityException.class)
    public ResponseEntity<ApiError> handlerNotFoundException(MaxCapacityException ex) {
        log.error(ex.getLocalizedMessage());
        ApiError apiError = ex.getError();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        ApiError apiError = new ApiError(ErrorType.BUSINESS, ex.getConstraintViolations().stream().findFirst().get().getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintDefinitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiError> handleConstraintDefinitionException(ConstraintDefinitionException ex) {
        log.error(ex.getMessage());
        ApiError apiError = new ApiError(ErrorType.BUSINESS, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        log.error(ex.getMessage());
        ApiError apiError = ex.getError();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(ErrorType.BUSINESS, Messages.BAD_REQUEST_INVALID_DATA);
        apiError.getErrorDetailList().addAll(ex.getBindingResult().getFieldErrors().stream().map(f ->
                new ApiErrorDetail(f.getCode(), f.getDefaultMessage(), f.getField(),
                        f.getObjectName())
        ).collect(Collectors.toList()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(ErrorType.BUSINESS, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Values entered are invalid", ex);
        ApiError apiError = new ApiError(ErrorType.BUSINESS, ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

}
