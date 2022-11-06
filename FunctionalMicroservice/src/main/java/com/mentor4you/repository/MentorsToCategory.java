package com.mentor4you.repository;

import com.mentor4you.model.Mentors;
import com.mentor4you.model.Mentors_to_categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorsToCategory extends JpaRepository<Mentors_to_categories ,Integer> {

    List<Mentors_to_categories> removeByMentors(Mentors mentor);
}
