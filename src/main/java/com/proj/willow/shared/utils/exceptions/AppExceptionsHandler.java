package com.proj.willow.shared.utils.exceptions;

import com.proj.willow.model.response.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class AppExceptionsHandler {
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

   /* @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }*/


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleCommonException(Exception ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(new Date(),ex.getMessage()));
    }

     //Invalid media types
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        log.error(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE), ex.getContentType());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getContentType().toString()));
    }

      @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error(HttpStatus.NOT_FOUND + ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getRequestURL()));
    }

    //Check validations if you add validation rules on entity class
   @ExceptionHandler(value=ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage());
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                errorMessages.toString()));
    }

    //Check validations if you add validation rules on DTO class
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                errorMessages.toString()));
    }

            @ExceptionHandler(NotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String handleNotFound() {
        return “not_found”;
        }

        @ExceptionHandler(AccessDeniedException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public String handleAccessDenied() {
        return “access_denied”;
        }
    */

}
