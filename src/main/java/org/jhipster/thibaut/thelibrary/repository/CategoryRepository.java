package org.jhipster.thibaut.thelibrary.repository;

import org.jhipster.thibaut.thelibrary.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
