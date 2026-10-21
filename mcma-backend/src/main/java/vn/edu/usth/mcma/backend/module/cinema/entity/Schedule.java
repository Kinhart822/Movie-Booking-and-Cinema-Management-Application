package vn.edu.usth.mcma.backend.module.cinema.entity;

import java.time.Instant;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;
import vn.edu.usth.mcma.backend.module.movie.entity.Movie;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Schedule extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column private Instant startDateTime;

    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
