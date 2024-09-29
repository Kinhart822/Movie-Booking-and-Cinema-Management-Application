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
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20)
    private String name;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screen")
    private List<ScreenType> screenTypeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screen")
    private List<Seat> seatList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screen")
    private List<MovieSchedule> movieScheduleList = new ArrayList<>();
}
