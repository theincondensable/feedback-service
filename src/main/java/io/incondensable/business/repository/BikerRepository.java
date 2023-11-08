package io.incondensable.business.repository;

import io.incondensable.business.model.domain.Biker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author abbas
 */
@Repository
public interface BikerRepository extends JpaRepository<Biker, Long> {
}
