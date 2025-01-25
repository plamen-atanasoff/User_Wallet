package org.plamen.userwallet.utils;

import org.plamen.userwallet.exception.InvalidEntityDataException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandling {
    public static void handleValidationErrors(Errors errors) {
        if (errors.hasErrors()) {
            List<String> fieldErrorMessages = errors.getFieldErrors().stream()
                .map(err -> String.format(
                    "%s for: '%s' = '%s'", err.getDefaultMessage(), err.getField(), err.getRejectedValue()))
                .toList();
            List<String> globalErrorMessages = errors.getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

            List<String> errorMessages = new ArrayList<>(fieldErrorMessages);
            errorMessages.addAll(globalErrorMessages);
            throw new InvalidEntityDataException("Invalid wallet data", errorMessages);
        }
    }
}
