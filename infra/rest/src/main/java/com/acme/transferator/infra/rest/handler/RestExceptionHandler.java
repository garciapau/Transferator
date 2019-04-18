package com.acme.transferator.infra.rest.handler;

import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.infra.rest.model.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ApiError> handleRestClientException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildApiErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ApiError> handleRestClientException(NotEnoughFundsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildApiErrorResponse(ex.getMessage()));
    }

    private ApiError buildApiErrorResponse(String message) {
        return new ApiError.ApiErrorBuilder()
                .withMessage(message)
                .build();
    }

}
