package com.mentor4you.repository;

import com.mentor4you.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select user from User user")
    List<User> findAll();

    Optional<User> findByEmail(String email);

    User findOneById(int id);

    User findUserByEmail(String email);


}
