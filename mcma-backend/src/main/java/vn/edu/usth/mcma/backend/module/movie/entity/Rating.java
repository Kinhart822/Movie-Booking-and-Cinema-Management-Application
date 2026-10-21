package vn.edu.usth.mcma.backend.module.movie.entity;

import java.util.Set;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;
import vn.edu.usth.mcma.backend.module.booking.entity.Audience;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Rating extends AbstractAuditing {
    @Id private String id;
    @Column private String description;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany
    @JoinTable(
            name = "map_rating_audience",
            joinColumns = @JoinColumn(name = "rating_id"),
            inverseJoinColumns = @JoinColumn(name = "audience_id"))
    private Set<Audience> allowedAudiences;
}
