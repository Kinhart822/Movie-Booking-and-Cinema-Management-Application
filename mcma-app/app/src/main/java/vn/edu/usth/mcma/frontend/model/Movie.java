package vn.edu.usth.mcma.frontend.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Parcelable {
    private Long id;
    private String name;
    private Integer length;
    private String description;
    private Instant publishDate;
    private String trailerUrl;
    private String imageBase64;
    private String backgroundImageBase64;

    private List<Genre> genres;
    private List<Performer> performers;
    private Rating rating;
    private List<Review> reviews;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(length);
        parcel.writeString(description);
        parcel.writeSerializable(publishDate);
        parcel.writeString(trailerUrl);
        parcel.writeString(imageBase64);
        parcel.writeString(backgroundImageBase64);
        parcel.writeParcelableList(genres, 0);
        parcel.writeParcelableList(performers, 0);
        parcel.writeParcelable(rating, 0);
        parcel.writeParcelableList(reviews, 0);
    }
    protected Movie(Parcel in) {
        id = in.readLong();
        name = in.readString();
        length = in.readInt();
        description = in.readString();
        publishDate = (Instant) in.readSerializable();
        trailerUrl = in.readString();
        imageBase64 = in.readString();
        backgroundImageBase64 = in.readString();
        genres = new ArrayList<>();
        in.readParcelableList(genres, Genre.class.getClassLoader());
        performers = new ArrayList<>();
        in.readParcelableList(performers, Performer.class.getClassLoader());
        rating = in.readParcelable(Rating.class.getClassLoader());
        reviews = new ArrayList<>();
        in.readParcelableList(reviews, Review.class.getClassLoader());
    }
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
