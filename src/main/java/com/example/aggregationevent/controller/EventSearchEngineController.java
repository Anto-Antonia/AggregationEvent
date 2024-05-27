package com.example.aggregationevent.controller;

import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.service.EventSearchEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventSearch")
public class EventSearchEngineController {
    private final EventSearchEngineService eventSearchEngineService;

    public EventSearchEngineController(EventSearchEngineService eventSearchEngineService) {
        this.eventSearchEngineService = eventSearchEngineService;
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<EventsResponse>> filterEventsByKeyword(@RequestParam String keyword){
        List<EventsResponse> filteredEvents = eventSearchEngineService.filterEventsByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(filteredEvents);
    }

    @GetMapping("/title")
    public ResponseEntity<List<EventsResponse>> filterEventsByTitle(@RequestParam String title){
        List<EventsResponse> filteredEvents = eventSearchEngineService.filterEventsByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(filteredEvents);
    }

    @GetMapping("/filterById")
    public ResponseEntity<List<EventsResponse>> filterEventsByUserId(@RequestParam Integer id){  // ar trebui sa intoarca evenimente asociate cu id ul userului
        List<EventsResponse> filteredEvents = eventSearchEngineService.filterEventsByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(filteredEvents);
    }
}
