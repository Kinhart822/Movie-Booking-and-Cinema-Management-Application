package com.spring.entities;

import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import com.spring.enums.TicketType;
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
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Booking_No")
    private String bookingNo;

    @Column(name = "Start_Date_Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @Column(name = "Payment_Method")
    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_schedule_id")
    private MovieSchedule movieSchedule;

    @Column(name = "Ticket_Type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @ManyToMany
    @JoinTable(
            name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    public double getTicketPrice(TicketType ticketType) {
        return switch (ticketType) {
            case Adult -> 15.00;
            case Child -> 10.00;
            case Teen -> 12.00;
            case Senior -> 8.00;
            case Student -> 11.00;
            case Couple_Ticket -> 25.00;
            case Family_Ticket -> 40.00;
            default -> throw new IllegalArgumentException("Invalid ticket type");
        };
    }
}


