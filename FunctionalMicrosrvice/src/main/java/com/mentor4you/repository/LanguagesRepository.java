package com.mentor4you.repository;

import com.mentor4you.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Integer> {

    @Query("Select a from Languages a")
    List<Languages> findAllLanguages();

    @Query("select a.name from Languages a")
    List<String> findAllLanguagesName();

    Languages findByName(String name);
}