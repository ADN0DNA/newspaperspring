package org.example.newspaperspring.domain.error;

public class DuplicatedUsernameError extends RuntimeException {
    public DuplicatedUsernameError(String message) {
        super(message);
    }
}
