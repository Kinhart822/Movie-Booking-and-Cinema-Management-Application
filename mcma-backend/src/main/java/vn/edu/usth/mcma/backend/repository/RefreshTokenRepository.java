package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.RefreshToken;
import vn.edu.usth.mcma.backend.entity.User;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByUserAndStatus(User user, Integer status);
    List<RefreshToken> findAllByUserAndStatus(User user, Integer status);
}
