package vn.edu.usth.mcma.backend.entity;

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
@Table(name = "map_booking_concession")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingConcession {
    @EmbeddedId private BookingConcessionPK id;
    @Column private Integer quantity;
}
