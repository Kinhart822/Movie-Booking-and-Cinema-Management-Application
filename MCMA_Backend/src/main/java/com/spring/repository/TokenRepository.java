package com.spring.repository;

import com.spring.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
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
