package vn.edu.usth.mcma.backend.module.movie.entity;

import java.util.Set;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Genre extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String name;
    @Column private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String imageBase64;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(mappedBy = "genreSet")
    private Set<Movie> movieSet;
}
