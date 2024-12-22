package vn.edu.usth.mcma.backend.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class SeatPositionValidate implements Comparable<SeatPositionValidate> {
    private int row;
    private int col;
    private int typeId;
    @Builder.Default
    private boolean isChecked = false;

    @Override
    public int compareTo(@NotNull SeatPositionValidate o) {
        if (this.row != o.row) {
            return Integer.compare(this.row, o.getRow());
        }
        return Integer.compare(this.col, o.getCol());
    }
}
