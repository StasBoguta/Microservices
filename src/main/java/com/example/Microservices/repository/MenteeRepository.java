package com.example.Microservices.repository;


import com.example.Microservices.model.Mentees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentees,Integer> {
}
