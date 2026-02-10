package com.example.dependabletaskapi.exception;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns404() {
        TaskNotFoundException exception = new TaskNotFoundException(123L);
        
        ResponseEntity<Map<String, String>> response = handler.handleNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
        assertTrue(response.getBody().get("error").contains("123"));
    }

    @Test
    void handleNotFound_hasCorrectMessage() {
        TaskNotFoundException exception = new TaskNotFoundException(456L);
        
        ResponseEntity<Map<String, String>> response = handler.handleNotFound(exception);
        
        assertEquals("Task not found: 456", response.getBody().get("error"));
    }

    @Test
    void handleGeneric_returns500() {
        Exception exception = new RuntimeException("Test error");
        
        ResponseEntity<Map<String, String>> response = handler.handleGeneric(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Internal server error", response.getBody().get("error"));
    }

    @Test
    void handleGeneric_withDifferentExceptions() {
        Exception exception1 = new IllegalArgumentException("Invalid input");
        Exception exception2 = new NullPointerException("Null value");
        
        ResponseEntity<Map<String, String>> response1 = handler.handleGeneric(exception1);
        ResponseEntity<Map<String, String>> response2 = handler.handleGeneric(exception2);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response2.getStatusCode());
        assertEquals("Internal server error", response1.getBody().get("error"));
        assertEquals("Internal server error", response2.getBody().get("error"));
    }

    @Test
    void handleTaskNotFound_withDifferentIds() {
        TaskNotFoundException exception1 = new TaskNotFoundException(1L);
        TaskNotFoundException exception2 = new TaskNotFoundException(999L);
        
        ResponseEntity<Map<String, String>> response1 = handler.handleNotFound(exception1);
        ResponseEntity<Map<String, String>> response2 = handler.handleNotFound(exception2);
        
        assertEquals("Task not found: 1", response1.getBody().get("error"));
        assertEquals("Task not found: 999", response2.getBody().get("error"));
    }
}
