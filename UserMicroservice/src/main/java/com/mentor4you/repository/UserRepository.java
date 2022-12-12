package com.mentor4you.repository;

import com.mentor4you.domain.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findUserByEmail(String email);
}
