package org.plamen.userwallet.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonexistentEntityException extends RuntimeException {
    public NonexistentEntityException(String message) {
        super(message);
    }

    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentEntityException(Throwable cause) {
        super(cause);
    }
}
