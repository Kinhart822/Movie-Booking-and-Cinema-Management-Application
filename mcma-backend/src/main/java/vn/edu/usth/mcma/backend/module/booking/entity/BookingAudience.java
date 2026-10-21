package vn.edu.usth.mcma.backend.module.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "map_booking_audience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingAudience {
    @EmbeddedId private BookingAudiencePK id;
    @Column private Integer quantity;
}
