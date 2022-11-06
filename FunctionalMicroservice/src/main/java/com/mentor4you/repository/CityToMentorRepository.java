package com.mentor4you.repository;

import com.mentor4you.model.CityToMentors;
import com.mentor4you.model.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CityToMentorRepository extends JpaRepository<CityToMentors,Integer> {

    @Transactional
    @Modifying
    @Query("delete from CityToMentors b where b.mentors=:mentors")
    void deleteC(@Param("mentors") Mentors mentors);

}
