package com.banking.exceptiontest;

import com.exception.AccountNotFoundException;
import com.exception.GlobalExceptionHandler;
import com.exception.InvalidAmountException;
import com.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleAccountNotFound() {
        AccountNotFoundException ex = new AccountNotFoundException("ACC123");
        ResponseEntity<ApiResponse> response = handler.handleAccountNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    public void testHandleInvalidAmount() {
        InvalidAmountException ex = new InvalidAmountException("Invalid");
        ResponseEntity<ApiResponse> response = handler.handleInvalidAmount(ex);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testHandleGenericException() {
        Exception ex = new Exception("Error");
        ResponseEntity<ApiResponse> response = handler.handleGenericException(ex);

        assertEquals(500, response.getStatusCodeValue());
    }
}
