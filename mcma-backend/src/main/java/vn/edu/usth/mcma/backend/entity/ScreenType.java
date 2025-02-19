package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ScreenType extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column
    private String description;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
    @Column
    private Double unitPrice;
}
