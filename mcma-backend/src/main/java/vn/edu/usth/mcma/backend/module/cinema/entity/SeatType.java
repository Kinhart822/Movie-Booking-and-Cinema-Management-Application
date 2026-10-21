package vn.edu.usth.mcma.backend.module.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;

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
public class SeatType extends AbstractAuditing {
    @Id private String id;
    @Column private String description;
    @Column private int width;
    @Column private int height;
    @Column private Double unitPrice;
}
