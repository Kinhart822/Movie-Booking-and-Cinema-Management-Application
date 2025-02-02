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
@SuperBuilder(toBuilder = true)
public class Cinema extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    @Column
    private String name;
    @Column
    private String address;
    @Column(columnDefinition = "LONGTEXT")
    private String imageBase64;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @ManyToMany
    @JoinTable(
            name = "map_cinema_concession",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "concession_id"))
    private Set<Concession> concessionSet;
}
