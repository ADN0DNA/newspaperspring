package org.example.newspaperspring.domain.error;

public class AppError extends RuntimeException{
    public AppError(String message) {
        super(message);
    }
}
