package vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Seat;

import lombok.Data;

@Data
public class UnavailableSeatResponse {
    private String unAvailableSeat;
    private Integer seatColumn;
    private Integer seatRow;
}
