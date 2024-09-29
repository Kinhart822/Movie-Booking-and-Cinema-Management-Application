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
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePublish;

    @Column(name = "Trailer_Link")
    private String trailerLink;

    @Column(name = "Created_By")
    @Enumerated(EnumType.ORDINAL)
    private Type createdBy;

    @Column(name = "Last_Modified_By")
    @Enumerated(EnumType.ORDINAL)
    private Type lastModifiedBy;

    @Column(name = "Date_Created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<MovieSchedule> movieScheduleList = new ArrayList<>();

    @ManyToMany(mappedBy = "movieSet")
    private Set<MovieGenre> movieGenreSet;

    @ManyToMany(mappedBy = "movieSet")
    private Set<MoviePerformer> moviePerformerSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieRatingDetail_id", nullable = false)
    private MovieRatingDetail movieRatingDetail;

    @ManyToMany(mappedBy = "movieSet")
    private Set<MovieCoupon> movieCouponSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<MovieRespond> movieResponds = new ArrayList<>();
}
