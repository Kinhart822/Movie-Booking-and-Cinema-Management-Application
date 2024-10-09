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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 50)
    private String name;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Screen> cinemaList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Drink> drinks = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<BookingDraft> bookingDrafts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Movie> movieList;
}
