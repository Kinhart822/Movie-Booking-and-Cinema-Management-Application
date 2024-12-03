package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private SeatPK id;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer typeId;
    @Column
    private String name;
}
