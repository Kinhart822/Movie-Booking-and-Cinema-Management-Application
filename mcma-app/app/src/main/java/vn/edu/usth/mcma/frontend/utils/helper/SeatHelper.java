package vn.edu.usth.mcma.frontend.utils.helper;

import java.util.List;
import java.util.Objects;

import vn.edu.usth.mcma.frontend.constant.SeatAvailables;
import vn.edu.usth.mcma.frontend.model.item.SeatItem;
import vn.edu.usth.mcma.frontend.model.parcelable.SeatParcelable;

public class SeatHelper {
    public static int getNumberOfAudiencePerSeat(SeatItem seat) {
        return (Objects.equals(seat.getTypeId(), SeatAvailables.LOVERS.name()) ||
                Objects.equals(seat.getTypeId(), SeatAvailables.BED.name()))
                ? 2
                : 1;
    }
    public static int getNumberOfAudiencePerSeat(SeatParcelable seat) {
        return (Objects.equals(seat.getTypeId(), SeatAvailables.LOVERS.name()) ||
                Objects.equals(seat.getTypeId(), SeatAvailables.BED.name()))
                ? 2
                : 1;
    }
    public static List<SeatParcelable> getRootSeats(List<SeatParcelable> seats) {
        return seats.stream()
                .filter(s -> Objects.equals(s.getRow(), s.getRootRow()) && Objects.equals(s.getCol(), s.getRootCol()))
                .toList();
    }
}
