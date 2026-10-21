package vn.edu.usth.mcma.backend.module.concession.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "map_concession_food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConcessionFood {
    @EmbeddedId private ConcessionFoodPK id;
    @Column private Integer quantity;
}
