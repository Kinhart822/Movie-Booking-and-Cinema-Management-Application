package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "map_concession_food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConcessionFood implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ConcessionFoodPK id;
    @Column
    private Integer quantity;
}
