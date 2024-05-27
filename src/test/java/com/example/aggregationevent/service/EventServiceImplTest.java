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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;
    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void addEvent_whenSuccessful_returnEvent() {
        User user = new User();
        user.setId(1);

        AddEventRequest addEventRequest = new AddEventRequest("Swimming with sharks", 1);

        Event eventToSave = new Event();
        eventToSave.setTitle("Swimming with sharks");

        EventsResponse eventsResponse = new EventsResponse();
        eventsResponse.setTitle("Swimming with Sharks");

        when(userRepository.findById(addEventRequest.getUserId())).thenReturn(Optional.of(user));
        when(eventMapper.fromAddEventRequest(addEventRequest)).thenReturn(eventToSave);
        when(eventRepository.save(eventToSave)).thenReturn(eventToSave);
        when(eventMapper.fromEventsResponseEntity(eventToSave)).thenReturn(eventsResponse);

        EventsResponse response = eventService.addEvent(addEventRequest);

        assertNotNull(response);
        assertEquals("Swimming with Sharks", response.getTitle());
    }

    @Test
    public void getAllEvents_thenReturnAllEvents(){
        User user = new User();
        user.setId(1);

        User user2 = new User();
        user2.setId(2);

        Event event = new Event();
        event.setTitle("Sample event");

        Event event2 = new Event();
        event2.setTitle("Sample event2");

        List<Event> events = List.of(event, event2);

        EventsResponse eventsResponse = new EventsResponse("Sample event", 1);
        EventsResponse eventsResponse2 = new EventsResponse("Sample event2", 2);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventMapper.fromEventsResponseEntity(event)).thenReturn(eventsResponse);
        when(eventMapper.fromEventsResponseEntity(event2)).thenReturn(eventsResponse2);

        List<EventsResponse> eventsResponseList = eventService.getAllEvents();

        assertNotNull(eventsResponseList);
        assertEquals(2, eventsResponseList.size());
        assertEquals("Sample event", eventsResponseList.get(0).getTitle());
        assertEquals(1, eventsResponseList.get(0).getUserId());

        assertEquals("Sample event2", eventsResponseList.get(1).getTitle());
        assertEquals(2, eventsResponseList.get(1).getUserId());

        verify(eventRepository, times(1)).findAll();
        verify(eventMapper, times(1)).fromEventsResponseEntity(event);
        verify(eventMapper, times(1)).fromEventsResponseEntity(event2);
    }

    @Test
    public void deleteEventById_whenEventExists_thenVerifyDelete(){
        Integer eventId = 1;

        eventService.deleteEventById(eventId);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    public void getEventById_whenEventExists_thenReturnEvent(){
        int eventId = 1;

        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Sample event");

        User user = new User();
        user.setId(1);

        event.setUser(user);

        EventsResponse eventsResponse = new EventsResponse();
        eventsResponse.setTitle("Sample event");
        eventsResponse.setUserId(1);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.fromEventsResponseEntity(event)).thenReturn(eventsResponse);

        EventsResponse response = eventService.getEventById(eventId);

        assertNotNull(response);
        assertEquals("Sample event", response.getTitle());
        assertEquals(1, response.getUserId());
    }

    @Test
    public void getEventById_whenDoesntExist_throwRuntimeException(){
        int eventId = 1;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        EventsNotFoundException exception = assertThrows(EventsNotFoundException.class, ()-> eventService.getEventById(eventId));
        assertEquals("The event with id " + eventId+ " not found", exception.getMessage());
    }

    @Test
    public void updateEvent_WhenEventExists_updateEvent(){
        int eventId = 1;

        UpdateEventRequest updateEventRequest = new UpdateEventRequest();
        updateEventRequest.setTitle("Walk in the park");

        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Walk in the garden");

        User user = new User();
        user.setId(1);
        event.setUser(user);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);

        eventService.updateEvent(eventId, updateEventRequest);

        verify(eventRepository, times(1)).save(event);
        assertEquals("Walk in the park", event.getTitle());
    }

    @Test
    public void updateEvent_whenEventDoesntExist_throwsException(){
        int eventId = 1;

        UpdateEventRequest updateEventRequest = new UpdateEventRequest();
        updateEventRequest.setTitle("Updated event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        EventsNotFoundException exception = assertThrows(EventsNotFoundException.class, ()-> eventService.updateEvent(eventId, updateEventRequest));
        assertEquals("The event with the id " + eventId + " not found", exception.getMessage());
    }
}
