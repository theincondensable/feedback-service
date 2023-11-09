package io.incondensable.business.repository;

import io.incondensable.business.model.domain.Biker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface BikerRepository extends JpaRepository<Biker, Long> {

    Optional<Biker> findBikerByUser_Id(Long userId);

}
