package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class SeatResponse {
    private Integer row;
    private Integer col;
    private String typeId;
    private String name;
    private Integer rootRow;
    private Integer rootCol;
    private String availability;
}
