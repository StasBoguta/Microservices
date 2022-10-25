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



    Optional<User> findById(int id);

    @Query("Select user from User user")
    List<User> findAll();

    Optional<User> findByEmail(String email);

    User findOneById(int id);

    User findUserByEmail(String email);


    List<User> findByBan(Boolean bool);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.last_name = ?1 WHERE u.id = ?2 and u.email like ?3")
    void updateUser(String name, int id, String email);
}
