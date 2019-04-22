package org.jhipster.thibaut.thelibrary.repository;

import org.jhipster.thibaut.thelibrary.domain.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
