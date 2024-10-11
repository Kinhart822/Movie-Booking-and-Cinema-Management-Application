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
public class BookingDraftDrink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_draft_id")
    private BookingDraft bookingDraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private SizeFoodOrDrink sizeDrink;
}
