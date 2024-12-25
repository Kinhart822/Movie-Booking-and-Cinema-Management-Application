package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatPosition {
    private int row;
    private int col;
    private int typeId;
    @JsonIgnore
    private String name;
}
