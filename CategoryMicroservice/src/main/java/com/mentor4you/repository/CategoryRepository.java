package com.mentor4you.repository;

import com.mentor4you.domain.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE categories SET creator_email = :newEmail WHERE creator_id = :creatorId",
            nativeQuery = true)
    void updateAuthorEmail(@Param("creatorId") Integer creatorId,
                           @Param("newEmail") String newEmail);
}
