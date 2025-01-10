package vn.edu.usth.mcma.frontend.components.Showtimes.Models;

import vn.edu.usth.mcma.R;

public enum SeatType {
    STAND(R.drawable.ic_seat_standard),
    VIP(R.drawable.ic_seat_vip),
    COUPLE(R.drawable.ic_seat_couple),
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