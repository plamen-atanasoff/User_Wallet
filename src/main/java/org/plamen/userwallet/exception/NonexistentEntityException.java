package org.plamen.userwallet.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NonexistentEntityException extends RuntimeException {
    public NonexistentEntityException(String message) {
        super(message);
    }

}
