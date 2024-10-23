package com.spring.repository;

import com.spring.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.isLoggedOut = true")
    List<Token> findAllLoggedOutTokensByUser(Integer userId);

    @Query("""
             select t from Token t\s
             inner join User u on t.user.id = u.id
             where t.user.id = :userId and t.isLoggedOut = false
            \s""")
    List<Token> findAllValidTokenByUser(Integer userId);

    @Query("""
             select t from Token t\s
             inner join User u on t.user.id = u.id
             where t.user.id = :userId
            \s""")
    List<Token> findAllByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
