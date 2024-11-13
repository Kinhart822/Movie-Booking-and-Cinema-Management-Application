package vn.edu.usth.mcma.service.common.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "movie_genre_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieGenreDetail extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
