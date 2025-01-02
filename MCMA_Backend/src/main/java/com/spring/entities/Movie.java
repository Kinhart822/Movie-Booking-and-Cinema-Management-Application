package com.spring.entities;

import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "Length")
    private Integer length;

    @Column(name = "Description", length = 1000)
    private String description;

    @Column(name = "Date_Publish")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePublish;

    @Column(name = "Image_Url", length = 1000)
    private String imageUrl;

    @Column(name = "Background_Image_Url", length = 1000)
    private String backgroundImageUrl;

    @Column(name = "Trailer_Link")
    private String trailerLink;

    @Column(name = "Created_By")
    @Enumerated(EnumType.ORDINAL)
    private Type createdBy;

    @Column(name = "Last_Modified_By")
    @Enumerated(EnumType.ORDINAL)
    private Type lastModifiedBy;

    @Column(name = "Date_Created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieSchedule> movieScheduleList;

    @ManyToMany
    @JoinTable(
            name = "set_movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movieGenre_id"))
    private Set<MovieGenre> movieGenreSet;

    @ManyToMany
    @JoinTable(
            name = "set_movie_performer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "moviePerformer_id"))
    private Set<MoviePerformer> moviePerformerSet;

    @ManyToMany
    @JoinTable(
            name = "set_movie_rating_detail",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movieRatingDetail_id"))
    private Set<MovieRatingDetail> movieRatingDetailSet;

    @ManyToMany
    @JoinTable(
            name = "set_movie_coupon",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> movieCouponSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieRespond> movieResponds;

    //TODO:USER
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL)
    private List<City> cityList;
    //TODO:USER
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Booking> bookings;
}
