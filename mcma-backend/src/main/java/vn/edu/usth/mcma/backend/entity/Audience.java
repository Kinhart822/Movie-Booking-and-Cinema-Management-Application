package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
//todo discounted audience with method
public class Audience extends AbstractAuditing implements Serializable, Comparable<Audience> {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column
    private String description;
    @Column
    private Double unitPrice;
    @Column
    private Integer ageLowerBound;
    @Column
    private Integer ageHigherBound;

    @Override
    public int compareTo(@NotNull Audience o) {
        return this.ageLowerBound.compareTo(o.ageLowerBound);
    }
}
