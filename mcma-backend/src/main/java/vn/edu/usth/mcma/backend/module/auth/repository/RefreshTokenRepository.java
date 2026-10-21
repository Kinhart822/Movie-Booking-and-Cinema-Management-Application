package vn.edu.usth.mcma.backend.module.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.auth.entity.RefreshToken;
import vn.edu.usth.mcma.backend.module.auth.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByUserAndStatus(User user, Integer status);

    List<RefreshToken> findAllByUserAndStatus(User user, Integer status);
}
