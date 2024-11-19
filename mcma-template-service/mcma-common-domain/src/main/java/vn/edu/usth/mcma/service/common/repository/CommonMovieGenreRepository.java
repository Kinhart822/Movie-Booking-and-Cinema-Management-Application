package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.MovieGenre;
import vn.edu.usth.mcma.service.common.dao.MovieGenrePK;

@Repository
public interface CommonMovieGenreRepository extends JpaRepository<MovieGenre, MovieGenrePK> {
}
