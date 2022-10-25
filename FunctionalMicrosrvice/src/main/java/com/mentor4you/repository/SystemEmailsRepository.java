package com.mentor4you.repository;

import com.mentor4you.model.SystemEmails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemEmailsRepository extends JpaRepository<SystemEmails, Integer> {

    @Query("Select a.value from SystemEmails a where a.id = ?1")
    String findEmailById(int id);
}
