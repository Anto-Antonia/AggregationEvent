package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.entity.Event;
import com.example.aggregationevent.mapper.EventMapper;
import com.example.aggregationevent.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventSearchEngineTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventSearchEngineImpl eventSearchEngine;

    @Mock
    private EventMapper eventMapper;

    private Event event;
    private EventsResponse eventsResponse;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1);
        event.setTitle("Sample event");

        eventsResponse = new EventsResponse();
        eventsResponse.setTitle("Sample event");
    }

    @Test
    public void filterEventsByKeyword_whenEventsExist_thenReturnEventResponse() {
        String keyword = "sample";

        when(eventRepository.findByTitleContainingKeyword(keyword)).thenReturn(List.of(event));
        when(eventMapper.fromEventsResponseEntity(event)).thenReturn(eventsResponse);

        List<EventsResponse> responses = eventSearchEngine.filterEventsByKeyword(keyword);

        assertEquals(1, responses.size());
        assertEquals("Sample event", responses.get(0).getTitle());

        verify(eventRepository, times(1)).findByTitleContainingKeyword(keyword);
        verify(eventMapper, times(1)).fromEventsResponseEntity(event);

    }

    @Test
    public void filterEventsByTitle_whenEventsExist_thenReturnEventResponse(){
        String title = "event";

        when(eventRepository.findByTitleContainingIgnoreCase(title)).thenReturn(List.of(event));
        when(eventMapper.fromEventsResponseEntity(event)).thenReturn(eventsResponse);

        List<EventsResponse> responses = eventSearchEngine.filterEventsByTitle(title);

        assertEquals(1, responses.size());
        assertEquals("Sample event", responses.get(0).getTitle());

        verify(eventRepository, times(1)).findByTitleContainingIgnoreCase(title);
        verify(eventMapper, times(1)).fromEventsResponseEntity(event);
    }

    @Test
    public void findEventByUserId_whenEventExist_thenReturnEventResponse(){
        Integer userId = 1;

        when(eventRepository.findByUserId(userId)).thenReturn(List.of(event));
        when(eventMapper.fromEventsResponseEntity(event)).thenReturn(eventsResponse);

        List<EventsResponse> responses = eventSearchEngine.filterEventsByUserId(userId);

        assertEquals(1, responses.size());
        assertEquals("Sample event", responses.get(0).getTitle());

        verify(eventRepository, times(1)).findByUserId(userId);
        verify(eventMapper, times(1)).fromEventsResponseEntity(event);
    }

}
