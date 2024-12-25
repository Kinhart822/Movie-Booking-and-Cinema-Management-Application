package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatPosition {
    private int row;
    private int col;
    private int typeId;
    @JsonIgnore
    private String name;
}
