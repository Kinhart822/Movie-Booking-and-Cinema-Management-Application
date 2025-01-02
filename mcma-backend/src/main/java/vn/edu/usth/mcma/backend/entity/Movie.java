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
public class Movie extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String imageUrl;
    @Column
    private Integer length;
    @Column
    private Instant publishDate;
    @Column
    private Long ratingId;
    @Column
    private String trailerUrl;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @ManyToMany
    @JoinTable(
            name = "map_movie_coupon",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> couponSet;
}
