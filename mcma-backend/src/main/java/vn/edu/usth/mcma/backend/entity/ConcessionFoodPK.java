package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConcessionFoodPK {
    @ManyToOne
    @JoinColumn(name = "concession_id")
    private Concession concession;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
