package com.mentor4you.repository;

import com.mentor4you.domain.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {}
