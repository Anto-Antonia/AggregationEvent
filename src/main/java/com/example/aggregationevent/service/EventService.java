package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.request.events.AddEventRequest;
import com.example.aggregationevent.dto.request.events.UpdateEventRequest;
import com.example.aggregationevent.dto.response.events.EventsResponse;

import java.util.List;

public interface EventService {
    List<EventsResponse> getAllEvents();
    EventsResponse getEventById(Integer id);
    EventsResponse addEvent(AddEventRequest addEventRequest);
    void deleteEventById(Integer id);
    void updateEvent(Integer id, UpdateEventRequest updateEventRequest);
}
