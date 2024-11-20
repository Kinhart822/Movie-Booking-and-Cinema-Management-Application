package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "city_food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityFood implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private CityFoodPK id;
}
