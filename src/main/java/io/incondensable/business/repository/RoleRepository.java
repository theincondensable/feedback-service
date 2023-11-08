package io.incondensable.business.repository;

import io.incondensable.business.model.auth.CustomerRole;
import io.incondensable.global.security.vo.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author abbas
 */
@Repository
public interface RoleRepository extends JpaRepository<CustomerRole, Short> {

    CustomerRole findAllByRole(RoleEnum role);

}
