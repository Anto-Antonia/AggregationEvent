package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.entity.Event;
import com.example.aggregationevent.mapper.EventMapper;
import com.example.aggregationevent.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventSearchEngineImpl implements EventSearchEngineService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    public EventSearchEngineImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventsResponse> filterEventsByKeyword(String keyword) {
        List<Event> events = eventRepository.findByTitleContainingKeyword(keyword);
        return events.stream().map(eventMapper::fromEventsResponseEntity).collect(Collectors.toList());
    }

    @Override
    public List<EventsResponse> filterEventsByTitle(String title) {
        List<Event> events = eventRepository.findByTitleContainingIgnoreCase(title);
        return events.stream().map(eventMapper::fromEventsResponseEntity).collect(Collectors.toList());
    }

    @Override
    public List<EventsResponse> filterEventsByUserId(Integer id) {
        List<Event> events = eventRepository.findByUserId(id);
        return events.stream().map(eventMapper::fromEventsResponseEntity).collect(Collectors.toList());
    }
}
