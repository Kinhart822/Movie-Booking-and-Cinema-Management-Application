package vn.edu.usth.mcma.backend.module.movie.entity;

import java.time.Instant;
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
public class Performer extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String name;

    @Column(columnDefinition = "TINYINT")
    private Integer typeId;

    @Column(columnDefinition = "TINYINT")
    private Integer sex;

    @Column(name = "dob")
    private Instant dateOfBirth;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(mappedBy = "performerSet")
    private Set<Movie> movieSet;
}
