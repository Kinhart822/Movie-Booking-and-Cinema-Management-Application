package com.spring.entities;

import com.spring.enums.SizeFoodOrDrink;
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
@Deprecated
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20)
    private String name;

    @Column(name = "Image_Url", length = 1000)
    private String imageUrl;

    @Column(name = "Description")
    private String description;

    @Column(name = "Size")
    @Enumerated(EnumType.STRING)
    private SizeFoodOrDrink size;

    @Column(name = "Volume")
    private Integer volume;

    @Column(name = "Price")
    private Double price;

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
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
    //rejected: unused
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drink")
    private List<BookingDrink> drinks;
}
