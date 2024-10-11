package com.spring.dto.Response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private List<String> ticketTypes;
    private List<String> ticketDescriptions;
}
