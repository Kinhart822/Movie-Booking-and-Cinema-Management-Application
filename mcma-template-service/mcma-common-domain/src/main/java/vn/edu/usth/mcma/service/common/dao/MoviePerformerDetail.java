package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "movie_performer_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviePerformerDetail extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer typeId;
    @Column
    private Integer sex;
    @Column(name = "dob")
    private Instant dateOfBirth;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
