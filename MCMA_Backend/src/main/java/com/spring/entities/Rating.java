package com.spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = "Rate must be at least 1")
    @Max(value = 5, message = "Rate cannot be more than 5")
    @Column(name = "ratingStar")
    private Double ratingStar;

    @OneToOne(mappedBy = "rating")
    private MovieRespond movieRespond;
}
