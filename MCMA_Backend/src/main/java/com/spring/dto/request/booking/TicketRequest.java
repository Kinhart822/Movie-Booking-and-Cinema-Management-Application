package com.spring.dto.request.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketRequest {
    private List<Integer> ticketIds;
}
