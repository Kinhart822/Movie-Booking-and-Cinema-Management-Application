package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.User;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCaseAndStatus(String email, Integer status);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByResetKeyAndStatusAndResetDueDateIsAfter(String resetKey, Integer userType, Instant time);
}
