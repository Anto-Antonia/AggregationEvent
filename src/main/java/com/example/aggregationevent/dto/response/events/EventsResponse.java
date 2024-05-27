package com.example.aggregationevent.dto.response.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsResponse {
    private String title;
    private Integer userId; // am schimbat din lista de stringuri in integer
}
