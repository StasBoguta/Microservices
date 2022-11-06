package com.mentor4you.repository;

import com.mentor4you.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("Select a from City a")
    List<City> findAllCity();

    @Query("select a.name from City a")
    List<String> findAllCityName();
}
