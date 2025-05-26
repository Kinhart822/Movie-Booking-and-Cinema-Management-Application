package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models;

import vn.edu.usth.mcma.R;

public enum SeatType {
    STAND(R.drawable.ic_seat_standard),
    VIP(R.drawable.ic_seat_vip),
    COUPLE(R.drawable.ic_seat_lovers),
    SOLD(R.drawable.ic_seat_sold),
    SELECTING(R.drawable.ic_seat_selecting);

    private final int drawableResId;

    SeatType(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public int getDrawableResId() {
        return drawableResId;
    }
}