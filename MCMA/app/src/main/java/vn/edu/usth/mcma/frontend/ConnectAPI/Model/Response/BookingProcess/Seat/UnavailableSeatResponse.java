package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

import lombok.Data;

@Data
public class UnavailableSeatResponse {
    private String unAvailableSeat;
    private Integer seatColumn;
    private Integer seatRow;
}
