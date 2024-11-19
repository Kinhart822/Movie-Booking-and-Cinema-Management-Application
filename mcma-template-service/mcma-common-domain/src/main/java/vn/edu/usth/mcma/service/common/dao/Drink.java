package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "drink")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drink extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long cinemaId;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String imageUrl;
    @Column
    private String size;
    @Column
    private Integer volume;
    @Column
    private Integer price;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
