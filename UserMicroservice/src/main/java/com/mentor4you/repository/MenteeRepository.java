package com.mentor4you.repository;

import com.mentor4you.domain.model.Mentee;
import org.springframework.data.repository.CrudRepository;

public interface MenteeRepository extends CrudRepository<Mentee, Integer> {}
