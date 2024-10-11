package com.spring.entities;

import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Start_Date_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @Column(name = "End_Date_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @Column(name = "Payment_Method")
    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Column(name = "Total_Price")
    private Double totalPrice;

    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_schedule_id")
    private MovieSchedule movieSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @OneToMany(mappedBy = "bookingDraft", cascade = CascadeType.ALL)
    private List<BookingDraftTicket> tickets;

    @OneToMany(mappedBy = "bookingDraft", cascade = CascadeType.ALL)
    private List<BookingDraftSeat> seatList;

    @OneToMany(mappedBy = "bookingDraft", cascade = CascadeType.ALL)
    private List<BookingDraftFood> foodList;

    @OneToMany(mappedBy = "bookingDraft", cascade = CascadeType.ALL)
    private List<BookingDraftDrink> drinks;

    @OneToMany(mappedBy = "bookingDraft", cascade = CascadeType.ALL)
    private List<BookingDraftCoupon> coupons;
}
