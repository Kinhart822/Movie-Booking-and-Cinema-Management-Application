package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import lombok.Data;

@Data
public class Seat {
    private Long id;
    private String name;
    private Integer typeId;
    private Integer row;
    private Integer col;
    private Integer rootRow;
    private Integer rootCol;
    private Integer availability;
}
