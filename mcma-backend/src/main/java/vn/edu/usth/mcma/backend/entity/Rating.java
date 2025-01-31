package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Rating extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @Column
    private String description;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @ManyToMany
    @JoinTable(
            name = "map_rating_audience",
            joinColumns = @JoinColumn(name = "rating"),
            inverseJoinColumns = @JoinColumn(name = "audience"))
    private Set<Audience> allowedAudiences;
}
