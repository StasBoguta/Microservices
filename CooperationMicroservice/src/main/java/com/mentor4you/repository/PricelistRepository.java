package com.mentor4you.repository;

import com.mentor4you.domain.model.Pricelist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PricelistRepository extends CrudRepository<Pricelist, Integer> {

  @Query("SELECT p FROM Pricelist p WHERE p.mentorId = :mentorId")
  Iterable<Pricelist> findAllPricelistsOfMentor(@Param("mentorId") Integer mentorId);
}
