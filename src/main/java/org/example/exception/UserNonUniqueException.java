package org.example.exception;

public class UserNonUniqueException extends RuntimeException{
    public UserNonUniqueException() {
    }

    public UserNonUniqueException(String message) {
        super(message);
    }
}
