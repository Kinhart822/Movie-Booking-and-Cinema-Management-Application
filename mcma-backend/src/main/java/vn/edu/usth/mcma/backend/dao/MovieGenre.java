package vn.edu.usth.mcma.backend.dao;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "movie_genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieGenre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private MovieGenrePK id;
}
