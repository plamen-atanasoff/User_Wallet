package org.plamen.userwallet.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class InvalidEntityDataException extends RuntimeException {
    private List<String> errorMessages = List.of();

    public InvalidEntityDataException(String message) {
        super(message);
    }

    public InvalidEntityDataException(String message, List<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }

    public InvalidEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityDataException(Throwable cause) {
        super(cause);
    }

    public InvalidEntityDataException(List<String> constraintViolations) {
        this.errorMessages = constraintViolations;
    }

    public InvalidEntityDataException(String message, Throwable cause, List<String> constraintViolations) {
        super(message, cause);
        this.errorMessages = constraintViolations;
    }

    public InvalidEntityDataException(Throwable cause, List<String> constraintViolations) {
        super(cause);
        this.errorMessages = constraintViolations;
    }
}
