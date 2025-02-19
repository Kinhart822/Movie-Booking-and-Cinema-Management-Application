package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.Data;

@Data
public class BookingSeatRequest {
    private Integer rootRow;
    private Integer rootCol;
}
