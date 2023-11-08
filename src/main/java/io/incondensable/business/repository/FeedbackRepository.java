package io.incondensable.business.repository;

import io.incondensable.business.model.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findFeedbackByDelivery_Id(Long deliveryId);

    @Query("select f from Feedback f join f.delivery as d on f.delivery.id = d.id order by d.createdOn asc")
    List<Feedback> findAllSortedByDeliveryCreatedDate();

    List<Feedback> findAllByBiker_Id(Long biker_id);

    List<Feedback> findAllByRating(Byte rating);

}
