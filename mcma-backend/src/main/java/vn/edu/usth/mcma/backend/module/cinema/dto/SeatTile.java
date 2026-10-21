package vn.edu.usth.mcma.backend.module.cinema.dto;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SeatTile implements Comparable<SeatTile> {
    private int row;
    private int col;
    private String typeId;
    private String name;
    private int rootRow;
    private int rootCol;
    @Builder.Default private boolean isChecked = false;

    @Override
    public int compareTo(@NotNull SeatTile o) {
        if (this.row != o.row) {
            return Integer.compare(this.row, o.getRow());
        }
        return Integer.compare(this.col, o.getCol());
    }
}
