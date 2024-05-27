package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.request.events.AddEventRequest;
import com.example.aggregationevent.dto.request.events.UpdateEventRequest;
import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.entity.Event;
import com.example.aggregationevent.entity.User;
import com.example.aggregationevent.exceptions.events.EventsNotFoundException;
import com.example.aggregationevent.mapper.EventMapper;
import com.example.aggregationevent.repository.EventRepository;
import com.example.aggregationevent.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<EventsResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventsResponse> eventsResponseEntities = events.stream().map(element -> eventMapper.fromEventsResponseEntity(element)).collect(Collectors.toList());

        return eventsResponseEntities;

    }

    @Override
    public EventsResponse getEventById(Integer id) {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if(eventOptional.isPresent()){

            Event event = eventOptional.get();
            EventsResponse eventsResponse = eventMapper.fromEventsResponseEntity(event);

            return eventsResponse;
        } else {
            throw new EventsNotFoundException("The event with id " + id+ " not found");
        }
    }

    @Override
    public EventsResponse addEvent(AddEventRequest addEventRequest) {

        Event event = eventMapper.fromAddEventRequest(addEventRequest);

        Optional<User> optionalUser = userRepository.findById(addEventRequest.getUserId());
        if(optionalUser.isPresent())
        {
            event.setUser(optionalUser.get());
        }

        eventRepository.save(event);

        EventsResponse eventsResponse = eventMapper.fromEventsResponseEntity(event);
        return eventsResponse;

    }

    @Override
    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void updateEvent(Integer id, UpdateEventRequest updateEventRequest) {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if(eventOptional.isPresent()){

            Event eventToUpdate = eventOptional.get();
            eventToUpdate.setTitle(updateEventRequest.getTitle());
            eventRepository.save(eventToUpdate);
        } else {
            throw new EventsNotFoundException("The event with the id " + id + " not found");
        }

    }
}
