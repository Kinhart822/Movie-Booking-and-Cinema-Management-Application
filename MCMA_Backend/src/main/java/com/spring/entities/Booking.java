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
@Deprecated
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Booking_No")
    private String bookingNo;
    //rejected: movie start time -> get from schedule
    @Column(name = "Start_Date_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
    //rejected: movie end time -> get from schedule
    @Column(name = "End_Date_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @Column(name = "Date_Created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateUpdated;

    @Column(name = "Payment_Method")
    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Column(name = "Total_Price")
    private Double totalPrice;

    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //rejected: get from schedule
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    //rejected: get from schedule
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    //rejected: get from schedule
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id")
    private MovieSchedule movieSchedule;
    //rejected: get from schedule
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    // rejected
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingTicket> tickets;
    // rejected
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingSeat> seatList;
    // rejected
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingFood> foodList;
    // rejected
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDrink> drinks;
    // rejected
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingCoupon> coupons;
}


