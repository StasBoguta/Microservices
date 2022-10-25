package com.example.Microservices.repository;

import com.example.Microservices.model.Mentees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<Mentees,Integer> { }
