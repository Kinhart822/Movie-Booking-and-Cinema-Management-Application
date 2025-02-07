package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Movie extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String overview;
    @Column(columnDefinition = "LONGTEXT")
    private String poster;
    @Column(columnDefinition = "LONGTEXT")
    private String banner;
    @Column
    private Integer length;
    @Column
    private Instant releaseDate;
    @Column
    private String trailerYoutubeId;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;
    @ManyToMany
    @JoinTable(
            name = "map_movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genreSet;
    @ManyToMany
    @JoinTable(
            name = "map_movie_performer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "performer_id"))
    private Set<Performer> performerSet;
}
