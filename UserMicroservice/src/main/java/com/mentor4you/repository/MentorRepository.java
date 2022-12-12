package com.mentor4you.repository;

import com.mentor4you.domain.model.Mentor;
import org.springframework.data.repository.CrudRepository;

public interface MentorRepository extends CrudRepository<Mentor, Integer> {}
