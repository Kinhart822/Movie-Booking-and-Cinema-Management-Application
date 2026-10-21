package vn.edu.usth.mcma.backend.dto.request.booking;

import lombok.Data;

@Data
public class BookingSeatRequest {
    private Integer rootRow;
    private Integer rootCol;
}
