package vn.edu.usth.mcma.backend.module.movie.entity;

import java.time.Instant;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import vn.edu.usth.mcma.backend.module.auth.entity.User;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Vote cannot be less than 1")
    @Max(value = 5, message = "Vote cannot be more than 10")
    @Column(columnDefinition = "TINYINT")
    private Integer userVote;

    @Column private String userComment;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column private Long lastModifiedBy;

    @Column(updatable = false)
    private Instant createdDate;

    @Column private Instant lastModifiedDate;
}
