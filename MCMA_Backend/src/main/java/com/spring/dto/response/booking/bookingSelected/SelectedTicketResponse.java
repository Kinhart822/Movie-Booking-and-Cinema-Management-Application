package com.spring.dto.response.booking.bookingSelected;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedTicketResponse {
    private List<Integer> ticketIds;
    private List<String> ticketTypes;
    private List<String> ticketDescriptions;
    private List<Double> ticketPrice;
    private List<Integer> quantityList;
    private Double totalPrice;
}
