package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.response.events.EventsResponse;

import java.util.List;

public interface EventSearchEngineService {
    List<EventsResponse> filterEventsByKeyword(String keyword);
    List<EventsResponse> filterEventsByTitle(String title);
    List<EventsResponse> filterEventsByUserId(Integer id);

}
