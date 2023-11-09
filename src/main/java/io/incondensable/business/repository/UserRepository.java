package io.incondensable.business.repository;

import io.incondensable.business.model.client.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("select count(c) >= 1 from User c where c.email = :email")
    boolean existsCustomerByEmail(@Param("email") String email);

}
