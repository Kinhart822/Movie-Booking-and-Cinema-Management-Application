package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SeatPosition {
    private int row;
    private int col;
    private int typeId;
    @JsonIgnore
    private String name;
}
