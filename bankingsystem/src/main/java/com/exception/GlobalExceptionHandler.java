package com.exception;

import com.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ------ Account Not Found ------
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), null, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ------ Invalid Amount ------
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse> handleInvalidAmount(InvalidAmountException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ------ Insufficient Balance ------
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ------ Generic Error (Fallback) ------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {

        ApiResponse response = new ApiResponse("Something went wrong: " + ex.getMessage(), null, false);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
