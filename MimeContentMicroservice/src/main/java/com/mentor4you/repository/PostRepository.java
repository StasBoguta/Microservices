package com.mentor4you.repository;

import com.mentor4you.domain.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts SET category_name = :newName WHERE category_id = :categoryId",
            nativeQuery = true)
    void updateCategoryName(@Param("categoryId") Integer categoryId,
                            @Param("newName") String newName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts SET author_email = :newEmail WHERE author_id = :authorId",
            nativeQuery = true)
    void updateAuthorEmail(@Param("authorId") Integer authorId,
                           @Param("newEmail") String newEmail);
}
