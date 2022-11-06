package com.mentor4you.repository;

import com.mentor4you.model.Mentees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentees ,Integer> {
}
