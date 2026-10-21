package vn.edu.usth.mcma.backend.module.cinema.entity;

import java.time.Instant;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.*;

/** not extending AbstractAuditing due to @SuperBuilder error */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Seat {
    @EmbeddedId private SeatPK id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private SeatType seatType;

    @Column private String name;
    @Column private Integer rootRow;
    @Column private Integer rootCol;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy @Column private Long lastModifiedBy;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;

    @LastModifiedDate @Column private Instant lastModifiedDate;
}
