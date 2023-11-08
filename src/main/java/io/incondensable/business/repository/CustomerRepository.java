package io.incondensable.business.repository;

import io.incondensable.business.model.client.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);

    @Query("select count(c) >= 1 from Customer c where c.email = :email")
    boolean existsCustomerByEmail(@Param("email") String email);

}
