package com.example.aggregationevent.exceptions.events;

public class EventsNotFoundException extends RuntimeException{
    public EventsNotFoundException(String message) {
        super(message);
    }
}
