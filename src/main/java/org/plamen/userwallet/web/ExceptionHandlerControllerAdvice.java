package org.plamen.userwallet.web;

import lombok.Getter;
import org.plamen.userwallet.exception.InvalidEntityDataException;
import org.plamen.userwallet.exception.NonexistentEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityDataException(InvalidEntityDataException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
            new ErrorResponse(BAD_REQUEST.value(), ex.getMessage(), ex.getErrorMessages()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNonexistentEntityException(NonexistentEntityException ex) {
        return ResponseEntity.status(NOT_FOUND).body(
            new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
            new ErrorResponse(INTERNAL_SERVER_ERROR.value(), ex.getMessage())
        );
    }

    @Getter
    public static class ErrorResponse {
        private final int status;
        private final String message;
        private List<String> errorMessages = List.of();

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public ErrorResponse(int status, String message, List<String> errorMessages) {
            this.status = status;
            this.message = message;
            this.errorMessages = errorMessages;
        }
    }
}
