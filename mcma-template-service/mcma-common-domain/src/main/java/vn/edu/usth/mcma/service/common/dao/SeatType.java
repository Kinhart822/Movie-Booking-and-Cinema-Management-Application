package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "seat_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatType extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
