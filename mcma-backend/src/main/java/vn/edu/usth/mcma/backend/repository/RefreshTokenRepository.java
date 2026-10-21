package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.RefreshToken;
import vn.edu.usth.mcma.backend.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByUserAndStatus(User user, Integer status);

    List<RefreshToken> findAllByUserAndStatus(User user, Integer status);
}
