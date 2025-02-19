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
public class Genre extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(columnDefinition = "LONGTEXT")
    private String imageBase64;
    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(mappedBy = "genreSet")
    private Set<Movie> movieSet;
}
