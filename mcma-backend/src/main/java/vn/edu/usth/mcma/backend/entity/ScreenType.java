package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ScreenType extends AbstractAuditing {
    @Id private String id;
    @Column private String description;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @Column private Double unitPrice;
}
