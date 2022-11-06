package com.mentor4you.repository;

import com.mentor4you.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestUserRepository extends JpaRepository<User, Long> {
    List<User> findAllByStatus(Boolean status, Pageable pageable);
    Page<User> findAllByBan(Boolean ban, Pageable pageable);

    @Query("select a from User a")
    Page<User> findAllUsers(Pageable pageable);

    @Query(value = "select * from animal", nativeQuery = true)
    Page<User> findAllUsersNative(Pageable pageable);


}