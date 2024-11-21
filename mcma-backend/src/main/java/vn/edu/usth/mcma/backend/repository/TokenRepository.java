package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dao.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND t.isLoggedOut = true")
    List<Token> findAllLoggedOutTokensByUser(Long userId);

    @Query("""
             select t from Token t\s
             inner join User u on t.userId = u.id
             where t.userId = :userId and t.isLoggedOut = false
            \s""")
    List<Token> findAllValidTokenByUser(Long userId);

    @Query("""
             select t from Token t\s
             inner join User u on t.userId = u.id
             where t.userId = :userId
            \s""")
    List<Token> findAllByUser(Long userId);

    Optional<Token> findByValue(String value);
}
