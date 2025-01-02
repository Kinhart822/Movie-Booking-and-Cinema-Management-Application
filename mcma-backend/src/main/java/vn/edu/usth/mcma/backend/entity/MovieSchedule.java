package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieSchedule extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long screenId;
    @Column
    private Long movieId;
    @Column
    private Instant startTime;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
