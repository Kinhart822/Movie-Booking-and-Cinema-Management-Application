package vn.edu.usth.mcma.backend.module.cinema.entity;

import java.util.Set;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;
import vn.edu.usth.mcma.backend.module.concession.entity.Concession;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Cinema extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column private String name;
    @Column private String address;

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
