package io.incondensable.business.repository;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author abbas
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAllByDeliveree(User deliveree);

}
