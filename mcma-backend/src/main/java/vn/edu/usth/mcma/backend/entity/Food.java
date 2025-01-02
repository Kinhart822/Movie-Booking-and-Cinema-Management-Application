package vn.edu.usth.mcma.backend.entity;

import constants.RefreshmentSize;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String imageUrl;
    @Column
    @Enumerated(EnumType.STRING)
    private RefreshmentSize size;
    @Column
    private Double price;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
}
