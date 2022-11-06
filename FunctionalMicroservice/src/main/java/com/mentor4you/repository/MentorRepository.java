package com.mentor4you.repository;

import com.mentor4you.model.Mentors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface MentorRepository

        extends JpaRepository<Mentors, Integer> {
    Optional<Mentors> findById(int id);

    @Query("Select distinct m.mentors from Mentors_to_categories m WHERE m.categories.name=?1")
    List<Mentors> findMentorsByCategoryName(String name);

    @Query(value = "Select distinct m from Mentors m ORDER BY m.rating DESC")
    List<Mentors> findMentorsBestRating(Pageable pageable);



    Mentors findOneById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Mentors m SET m.rating = ?1 WHERE m.id = ?2")
    void updateRating( double rating, int id);
}
