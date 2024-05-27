package com.example.aggregationevent.mapper;

import com.example.aggregationevent.dto.request.events.AddEventRequest;
import com.example.aggregationevent.dto.request.events.UpdateEventRequest;
import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event fromAddEventRequest(AddEventRequest addEventRequest){

        Event event = new Event();
        event.setTitle(addEventRequest.getTitle());

        return event;
    }

    public EventsResponse fromEventsResponseEntity(Event event){

        EventsResponse responseEntity = new EventsResponse();
        responseEntity.setTitle(event.getTitle());
        if(event.getUser() != null)
        {
            responseEntity.setUserId(event.getUser().getId());
        }


        return responseEntity;
    }

    public EventsResponse fromEventUpdateRequest(Event eventTarget, UpdateEventRequest updateEventRequest){
        eventTarget.setTitle(updateEventRequest.getTitle());

        EventsResponse eventsResponse = new EventsResponse();
        eventsResponse.setTitle(eventTarget.getTitle());
        return eventsResponse;
    }
}
