package com.mentor4you.repository;

import com.mentor4you.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> { }
