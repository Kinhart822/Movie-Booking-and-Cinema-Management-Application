package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatHelperOutput {
    private int row;
    private int col;
    private int typeId;
    private String name;
    private int rootRow;
    private int rootCol;
}
