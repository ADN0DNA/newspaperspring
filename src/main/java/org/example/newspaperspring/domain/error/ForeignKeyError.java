package org.example.newspaperspring.domain.error;

public class ForeignKeyError extends DatabaseError {
    public ForeignKeyError(String message) {
        super(message);
    }
}
