package vn.edu.usth.mcma.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "map_booking_audience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookingAudience implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private BookingAudiencePK id;
    @Column
    private Integer quantity;
}
