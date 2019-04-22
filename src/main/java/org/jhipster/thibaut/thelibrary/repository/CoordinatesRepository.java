package org.jhipster.thibaut.thelibrary.repository;

import org.jhipster.thibaut.thelibrary.domain.Coordinates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coordinates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {

}
