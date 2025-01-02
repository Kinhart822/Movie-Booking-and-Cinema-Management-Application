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

    @Column(name = "Address", length = 255)
    private String address;

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

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Screen> screenList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Food> foodList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Drink> drinks;
    //haven't imported: unused
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<Booking> bookings;
    //haven't imported: unused
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
    private List<MovieSchedule> movieSchedules;
}
