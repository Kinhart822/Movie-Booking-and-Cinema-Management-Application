package com.spring.dto.Request.booking;

import com.spring.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketRequest {
    private List<Integer> ticketIds;
    private List<TicketType> ticketTypes;
}
