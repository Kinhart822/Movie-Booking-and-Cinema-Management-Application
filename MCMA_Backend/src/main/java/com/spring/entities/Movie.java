package com.spring.entities;

import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
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

    @Column(name = "Date_Publish")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePublish;

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
    private List<MovieSchedule> movieScheduleList = new ArrayList<>();

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieRatingDetail_id")
    private MovieRatingDetail movieRatingDetail;

    @ManyToMany
    @JoinTable(
            name = "set_movie_coupon",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> movieCouponSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieRespond> movieResponds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
