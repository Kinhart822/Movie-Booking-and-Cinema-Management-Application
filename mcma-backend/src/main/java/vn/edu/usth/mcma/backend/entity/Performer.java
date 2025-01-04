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
@SuperBuilder
public class Performer extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
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
