package com.inditex.prices.infrastructure.in.rest.exception;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.generated.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(
            PriceNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected internal error",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            String path
    ) {
        var errorResponse = new ErrorResponse()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path);

        return ResponseEntity.status(status).body(errorResponse);
    }
}