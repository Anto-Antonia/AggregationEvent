package com.example.aggregationevent.dto.request.events;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {

    @NotEmpty(message = "The event name must not be empty")
    private String title;
    private Integer userId;
}
