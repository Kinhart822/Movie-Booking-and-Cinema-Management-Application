package com.spring.repository;

import com.spring.entities.User;
import com.spring.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer userId);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    List<User> findUserByEmail(@Param("email") String email);

    User findByUserType(Type type);
}
