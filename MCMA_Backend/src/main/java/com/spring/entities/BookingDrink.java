package com.spring.entities;

import com.spring.enums.SizeFoodOrDrink;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingDrink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private SizeFoodOrDrink sizeDrink;

    @Column(name = "quantity")
    private Integer quantity;
}
