package vn.edu.usth.mcma.frontend.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.frontend.model.item.ItemsOrderedItem;
import vn.edu.usth.mcma.frontend.model.parcelable.AudienceTypeParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.ConcessionParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.ItemsOrderedParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.SeatParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.SeatTypeParcelable;
import vn.edu.usth.mcma.frontend.utils.helper.SeatHelper;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Parcelable {
    //todo consider separate details
    private Long movieId;
    private String cinemaName;
    private String screenNameDateDuration;
    private String movieName;
    private String rating;
    private String ratingDescription;
    private String screenType;

    private String sessionId;
    private Long limitPlusCurrentElapsedBootTime;
    private Long scheduleId;
    @Getter
    private List<ItemsOrderedParcelable> itemsOrdered;
    private Map<String, SeatTypeParcelable> seatTypes;
    private List<SeatParcelable> seats;
    private List<AudienceTypeParcelable> audienceTypes;
    private List<ConcessionParcelable> concessions;
    @Override
    public int describeContents() {
        return 0;
    }
    protected Booking(Parcel in) {
        movieId = in.readLong();
        cinemaName = in.readString();
        screenNameDateDuration = in.readString();
        movieName = in.readString();
        rating = in.readString();
        ratingDescription = in.readString();
        screenType = in.readString();

        sessionId = in.readString();
        limitPlusCurrentElapsedBootTime = in.readLong();
        scheduleId = in.readLong();
        itemsOrdered = in.readParcelableList(itemsOrdered = new ArrayList<>(), ItemsOrderedParcelable.class.getClassLoader(), ItemsOrderedParcelable.class);
        in.readMap(seatTypes = new TreeMap<>(), SeatTypeParcelable.class.getClassLoader(), String.class, SeatTypeParcelable.class);
        seats = in.readParcelableList(seats = new ArrayList<>(), SeatParcelable.class.getClassLoader(), SeatParcelable.class);
        audienceTypes = in.readParcelableList(audienceTypes = new ArrayList<>(), AudienceTypeParcelable.class.getClassLoader(), AudienceTypeParcelable.class);
        concessions = in.readParcelableList(concessions = new ArrayList<>(), ConcessionParcelable.class.getClassLoader(), ConcessionParcelable.class);
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(cinemaName);
        dest.writeString(screenNameDateDuration);
        dest.writeString(movieName);
        dest.writeString(rating);
        dest.writeString(ratingDescription);
        dest.writeString(screenType);

        dest.writeString(sessionId);
        dest.writeLong(limitPlusCurrentElapsedBootTime == null ? 0L : limitPlusCurrentElapsedBootTime);
        dest.writeLong(scheduleId);
        dest.writeParcelableList(itemsOrdered, 0);
        dest.writeMap(seatTypes);
        dest.writeParcelableList(seats, 0);
        dest.writeParcelableList(audienceTypes, 0);
        dest.writeParcelableList(concessions, 0);
    }
    public static final Creator<Booking> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }
        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };
    public int getTotalAudience() {
        if (audienceTypes.isEmpty()) {
            return SeatHelper
                    .getRootSeats(this.seats).stream()
                    .mapToInt(SeatHelper::getNumberOfAudiencePerSeat)
                    .sum();
        }
        return audienceTypes.size();
    }
    public double getTotalPrice() {
        System.out.println(itemsOrdered);
        return itemsOrdered.stream()
                .mapToDouble(ItemsOrderedParcelable::getTotalPrice)
                .sum();
    }
    public Booking setSeats(List<SeatParcelable> seats) {
        this.seats = seats;
        Map<SeatTypeParcelable, List<SeatParcelable>> seatTypeSeatListMap = new TreeMap<>();
        //todo consider seats or rootSeats
        this.seats.forEach(seat -> {
            Optional<SeatTypeParcelable> seatTypeOpt = Optional.ofNullable(seatTypes.get(seat.getTypeId()));
            if (seatTypeOpt.isEmpty()) {
                //todo handle null (dialog...)
                return;
            }
            SeatTypeParcelable seatType = seatTypeOpt.get();
            seatTypeSeatListMap
                    .computeIfAbsent(seatType, v -> new ArrayList<>())
                    .add(seat);
        });
        seatTypeSeatListMap.forEach((seatType, seatsByType) -> {
            int size = seatsByType.size() / (seatType.getWidth() * seatType.getHeight());
            itemsOrdered.add(ItemsOrderedParcelable.builder()
                    .quantity(size)
                    .name(String.format("%s: %s", seatType.getId(), seatsByType.stream().map(SeatParcelable::getName).toList()))
                    .totalPrice(seatType.getUnitPrice() * size).build());
        });
        return this;
    }
    public Booking setAudienceTypes(List<AudienceTypeParcelable> audienceTypes) {
        this.audienceTypes = audienceTypes;
        this.audienceTypes.forEach(audienceType -> {
            if (audienceType.getQuantity() > 0) {
                itemsOrdered.add(ItemsOrderedParcelable.builder()
                        .quantity(audienceType.getQuantity())
                        .name(audienceType.getId())
                        .totalPrice(audienceType.getUnitPrice() * audienceType.getQuantity()).build());
            }
        });
        return this;
    }
    public Booking setConcessions(List<ConcessionParcelable> concessions) {
        this.concessions = concessions;
        this.concessions.forEach(concession -> {
            if (concession.getQuantity() > 0) {
                itemsOrdered.add(ItemsOrderedParcelable.builder()
                        .quantity(concession.getQuantity())
                        .name(concession.getName())
                        .totalPrice(concession.getComboPrice() * concession.getQuantity()).build());
            }
        });
        return this;
    }
}
