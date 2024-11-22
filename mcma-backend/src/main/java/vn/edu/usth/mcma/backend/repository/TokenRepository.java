package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dao.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            SELECT t FROM Token t
            inner join User u on t.userId = u.id
            WHERE u.email = :email AND t.isLoggedOut = true""")
    List<Token> findAllLoggedOutByEmail(@Param("email")String email);

    @Query("""
            select t from Token t
            inner join User u on t.userId = u.id
            where u.email = :email and t.isLoggedOut = false""")
    List<Token> findAllLoggedInByEmail(@Param("email")String email);

    @Query("""
             select t from Token t
             inner join User u on t.userId = u.id
             where t.userId = :id""")
    List<Token> findAllByUser(@Param("id")Long userId);

    Optional<Token> findByValue(String value);
}
