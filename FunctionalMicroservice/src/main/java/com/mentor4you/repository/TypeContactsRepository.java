package com.mentor4you.repository;

import com.mentor4you.model.TypeContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TypeContactsRepository extends JpaRepository<TypeContacts, Integer> {

    Optional<TypeContacts> findById(int id);

    @Query("Select sn from TypeContacts sn WHERE sn.name =?1")
    TypeContacts findByName(String name);
}
