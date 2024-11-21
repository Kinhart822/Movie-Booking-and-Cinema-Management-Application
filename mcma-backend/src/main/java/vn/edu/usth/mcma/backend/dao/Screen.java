package vn.edu.usth.mcma.backend.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "screen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long cinemaId;
    @Column
    private String name;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer typeId;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
