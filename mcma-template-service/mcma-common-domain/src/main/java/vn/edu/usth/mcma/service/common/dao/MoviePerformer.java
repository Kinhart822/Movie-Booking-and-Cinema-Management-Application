package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "movie_performer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviePerformer extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @EmbeddedId
    private MoviePerformerPK id;
}
