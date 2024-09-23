package com.spring.entities;

import com.spring.enums.RatingStar;
import com.spring.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieRespond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "User_Rating")
    @Enumerated(EnumType.STRING)
    private RatingStar ratings;

    @Column(name = "User_Comment")
    private String userComment;

    @Column(name = "Created_By")
    @Enumerated(EnumType.ORDINAL)
    private Status createdBy;

    @Column(name = "Last_Modified_By")
    @Enumerated(EnumType.ORDINAL)
    private Status lastModifiedBy;

    @Column(name = "Date_Created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public double getRatingAvgStar() {
        int star = 0;
        for (MovieRespond movieRating : movie.getMovieResponds()) {
            if (movieRating.getRatings() == null) {
                star += 0;
            } else {
                star += movieRating.getRatings().ordinal() + 1;
            }
        }
        return (double) star / (double) movie.getMovieResponds().size();
    }
}
