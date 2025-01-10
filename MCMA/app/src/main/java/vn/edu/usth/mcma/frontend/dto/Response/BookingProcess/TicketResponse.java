package vn.edu.usth.mcma.frontend.dto.Response.BookingProcess;

import java.util.List;

public class TicketResponse {
    private List<Integer> ticketIds;
    private List<String> ticketTypes;
    private List<String> ticketDescriptions;
    private List<Double> ticketPriceList;

    public List<Double> getTicketPriceList() {
        return ticketPriceList;
    }

    public void setTicketPriceList(List<Double> ticketPriceList) {
        this.ticketPriceList = ticketPriceList;
    }

    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public List<String> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<String> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<String> getTicketDescriptions() {
        return ticketDescriptions;
    }

    public void setTicketDescriptions(List<String> ticketDescriptions) {
        this.ticketDescriptions = ticketDescriptions;
    }
}
