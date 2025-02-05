package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatPresentation {
    private Integer row;
    private Integer col;
    private Integer typeId;
    private String name;
    private Integer rootRow;
    private Integer rootCol;
    private String availability;
}
