package vn.edu.usth.mcma.backend.module.booking.entity;

import jakarta.persistence.*;

import org.jetbrains.annotations.NotNull;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
// todo discounted audience with method
public class Audience extends AbstractAuditing implements Comparable<Audience> {
    @Id private String id;
    @Column private String description;
    @Column private Double unitPrice;
    @Column private Integer ageLowerBound;
    @Column private Integer ageHigherBound;

    @Override
    public int compareTo(@NotNull Audience o) {
        return this.ageLowerBound.compareTo(o.ageLowerBound);
    }
}
