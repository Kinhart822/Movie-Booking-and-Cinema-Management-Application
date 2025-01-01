package vn.edu.usth.mcma.frontend.Showtimes.Models;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.Objects;

import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.SeatStatus;

public class Seat implements Parcelable {
    private String id;
    private SeatType type;
    private SeatStatus status;
    private boolean isAvailable;

    public Seat(String id, SeatStatus status, boolean isAvailable) {
        this.id = id;
        this.status = status;
        this.isAvailable = isAvailable;
    }

    public Seat(String id, SeatType type, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    // Getters and setters


    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public SeatType getType() {
        return type;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public void setType(SeatType type) {
        this.type = type;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Parcelable implementation
    protected Seat(Parcel in) {
        id = in.readString();
        type = SeatType.valueOf(in.readString());
        isAvailable = in.readByte() != 0;
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type.name());
        dest.writeByte((byte) (isAvailable ? 1 : 0));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return isAvailable == seat.isAvailable &&
                Objects.equals(id, seat.id) &&
                type == seat.type;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, type, isAvailable);
    }
}
