package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.MovieGenre;
import vn.edu.usth.mcma.backend.entity.MovieGenrePK;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, MovieGenrePK> {
}
