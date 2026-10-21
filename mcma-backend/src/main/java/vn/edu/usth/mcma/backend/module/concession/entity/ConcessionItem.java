package vn.edu.usth.mcma.backend.module.concession.entity;

import jakarta.persistence.*;

import vn.edu.usth.mcma.backend.common.entity.AbstractAuditing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConcessionItem extends AbstractAuditing {
    @Column private String name;
    @Column private String description;
    @Column private Double unitPrice;
}
