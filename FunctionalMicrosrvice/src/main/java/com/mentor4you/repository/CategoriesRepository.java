package com.mentor4you.repository;

import com.mentor4you.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Integer> {
    Optional<Categories> findById(int id);


    @Query("Select a from Categories a")
    List<Categories> findAllCategory();

    @Query("Select a.name from Categories a")
    List<String> findAllCategoryName();

    void deleteByName(String name);
}
