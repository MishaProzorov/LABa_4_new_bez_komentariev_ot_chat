package com.example.SunriseSunset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**Global exception handler for handling application-specific exceptions.*/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**Handles validation exceptions for invalid request data.*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> response = new HashMap<>();
        response.put("error", String.format("A bad request error (status %d) occurred at %s: Validation failed.",
                HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
        response.put("cause", "The request contains invalid field values: " + errors.toString());
        response.put("solution", "Correct the field values according to the validation rules.");
        response.put("invalidExample", "Example with invalid data: {latitude=null, longitude=-5}");
        response.put("correctExample", "Example with correct data: {latitude=48.8566, longitude=2.3522}");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**Handles invalid date format exceptions.*/
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        String path = request.getDescription(false).replace("uri=", "");
        response.put("error", String.format("A bad request error (status %d) occurred at %s while accessing %s: Invalid date format.",
                HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), path));
        response.put("cause", "The 'date' parameter could not be parsed because it does not match the expected format: " + ex.getMessage());
        response.put("solution", "Ensure the 'date' parameter is in the format YYYY-MM-DD.");
        response.put("invalidExample", "Invalid: 2025-13-45");
        response.put("correctExample", "Correct: 2025-06-04");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**Handles exceptions from external API calls.*/
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Object> handleRestClientException(
            RestClientException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        String path = request.getDescription(false).replace("uri=", "");
        response.put("error", String.format("A service unavailable error (status %d) occurred at %s while accessing %s: Failed to fetch data from external API.",
                HttpStatus.SERVICE_UNAVAILABLE.value(), LocalDateTime.now(), path));
        response.put("cause", "The external API request failed: " + ex.getMessage());
        response.put("solution", "Check your network connection or try again later. If the issue persists, contact the API provider.");
        response.put("invalidExample", "Invalid: Attempting to connect without internet");
        response.put("correctExample", "Correct: Ensure a stable internet connection and retry");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    /**Handles illegal argument or state exceptions.*/
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        String path = request.getDescription(false).replace("uri=", "");
        response.put("error", String.format("A bad request error (status %d) occurred at %s while accessing %s: %s.",
                HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), path, ex.getMessage()));
        response.put("cause", "The request contains invalid parameters or data: " + ex.getMessage());
        response.put("solution", "Verify the input parameters and correct them according to the API documentation.");
        response.put("invalidExample", "Invalid: ID = -1");
        response.put("correctExample", "Correct: ID = 1");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**Handles all uncaught exceptions.*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
            Exception ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        String path = request.getDescription(false).replace("uri=", "");
        response.put("error", String.format("An internal server error (status %d) occurred at %s while accessing %s: An unexpected error occurred.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), path));
        response.put("cause", "An unexpected error occurred on the server: " + ex.getMessage());
        response.put("solution", "Please try again later or contact the support team with the error details.");
        response.put("invalidExample", "Invalid: Unexpected server failure");
        response.put("correctExample", "Correct: Retry after server recovery or contact support");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}