package vn.edu.usth.mcma.backend.module.cinema.entity;

import java.io.Serial;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Screen extends AbstractAuditing {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Column private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ScreenType screenType;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    // this attribute can only be mutated by schedule
    @Column private boolean mutable;
}
