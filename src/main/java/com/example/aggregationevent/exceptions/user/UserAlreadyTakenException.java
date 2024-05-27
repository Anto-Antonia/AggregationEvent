package com.example.aggregationevent.exceptions.user;

public class UserAlreadyTakenException extends RuntimeException{
    public UserAlreadyTakenException(String message) {
        super(message);
    }
}
