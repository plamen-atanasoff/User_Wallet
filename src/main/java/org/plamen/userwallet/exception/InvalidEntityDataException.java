package org.plamen.userwallet.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class InvalidEntityDataException extends RuntimeException {
    private List<String> errorMessages = List.of();

    public InvalidEntityDataException(String message, List<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }

}
