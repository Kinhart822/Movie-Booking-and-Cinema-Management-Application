package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(columnDefinition = "DECIMAL(3,2)")
    private Float discount;
    @Column(name = "min_spend_req")
    private Integer minimumSpendRequired;
    @Column
    private Integer discountLimit;
    @Column
    private Instant availableDate;
    @Column
    private Instant expiredDate;
    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(mappedBy = "couponSet")
    private Set<User> userSet;

    @ManyToMany(mappedBy = "couponSet")
    private Set<Movie> movieSet;
}
