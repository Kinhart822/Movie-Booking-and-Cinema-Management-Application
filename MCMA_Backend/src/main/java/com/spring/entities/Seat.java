package com.spring.entities;

import com.spring.enums.SeatType;
import com.spring.enums.Type;
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
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Seat_Row")
    private Integer row;

    @Column(name = "Seat_Column")
    private Integer column;

    @Column(name = "Type")
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @Column(name = "Name", length = 5)
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
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;
}
