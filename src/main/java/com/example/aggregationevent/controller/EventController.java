package com.example.aggregationevent.controller;

import com.example.aggregationevent.dto.request.events.AddEventRequest;
import com.example.aggregationevent.dto.request.events.UpdateEventRequest;
import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/event")
    public ResponseEntity<Void> addEvent(@RequestBody AddEventRequest addEventRequest){
        eventService.addEvent(addEventRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity <List<EventsResponse>> getAllEvents(){

        List<EventsResponse> responseList = eventService.getAllEvents();

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Integer id){
        eventService.deleteEventById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventsResponse> getEventById(@PathVariable Integer id){
        EventsResponse eventsResponse = eventService.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(eventsResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEvent (@PathVariable Integer id, @RequestParam @Valid UpdateEventRequest updateEventRequest){
        eventService.updateEvent(id,updateEventRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
