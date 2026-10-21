package vn.edu.usth.mcma.backend.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Coupon extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String name;
    @Column private String description;

    @Column(columnDefinition = "DECIMAL(3,2)")
    private Float discount;

    @Column(name = "min_spend_req")
    private Integer minimumSpendRequired;

    @Column private Integer discountLimit;
    @Column private Instant availableDate;
    @Column private Instant expiredDate;

    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
