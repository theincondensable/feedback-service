package io.incondensable.business.repository;

import io.incondensable.business.model.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select t from Token t where t.customer.id = :customerId")
    Optional<Token> findTokenByCustomerId(@Param("customerId") Long customerId);

}
