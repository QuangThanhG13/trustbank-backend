package org.example.accoutservice.commands.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class UniquenessValidationException extends RuntimeException {
    private final Set<FieldError> errors;
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param errors the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UniquenessValidationException(Set<FieldError> errors) {
        this.errors = errors;
    }
}
