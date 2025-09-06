package com.softmint;

import com.softmint.dto.ErrorResponse;
import com.softmint.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanApprovalException.class)
    public ResponseEntity<ErrorResponse> handleException(LoanApprovalException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(CreateEmployeeException.class)
    public ResponseEntity<ErrorResponse> handleException(CreateEmployeeException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(LoanApplicationException.class)
    public ResponseEntity<ErrorResponse> handleException(LoanApplicationException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ErrorResponse> handleException(CredentialsExpiredException ex) {
        //   ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ErrorResponse> handleException(AccountExpiredException ex) {
        //   ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handleException(LockedException ex) {
        //   ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleException(DisabledException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UnknownUserException.class)
    public ResponseEntity<ErrorResponse> handleException(UnknownUserException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleException(RefreshTokenException ex) {
        //  ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<ErrorResponse> handleException(ChangePasswordException ex) {
        //  ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(PasswordChangeRequiredException.class)
    public ResponseEntity<ErrorResponse> handleException(PasswordChangeRequiredException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_REQUIRED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(CustomLogonException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomLogonException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ResetPasswordException.class)
    public ResponseEntity<ErrorResponse> handleException(ResetPasswordException ex) {
        //  ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
        //  ex.printStackTrace();
        // Get the first validation error message
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input");

        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex) {
        // ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An internal server error occurred"));
    }
    // Add more exception handlers as needed
}

