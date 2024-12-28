package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

import java.util.List;

public class TicketResponse {
    private List<Integer> ticketIds;
    private List<String> ticketTypes;
    private List<Double> ticketPrices;
    private List<String> ticketDescriptions;
    public List<Double> getTicketPrices() {
        return ticketPrices;
    }


    public void setTicketPrices(List<Double> ticketPrices) {
        this.ticketPrices = ticketPrices;
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
