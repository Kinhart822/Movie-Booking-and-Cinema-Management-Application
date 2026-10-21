package vn.edu.usth.mcma.backend.entity;

import java.io.Serial;
import java.time.Instant;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Advertisement extends AbstractAuditing {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // combo
    @Column private Instant startDate;
    @Column private Instant endDate;
}
