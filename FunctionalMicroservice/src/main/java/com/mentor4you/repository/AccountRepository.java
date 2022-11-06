package com.mentor4you.repository;

import com.mentor4you.model.Accounts;
import com.mentor4you.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Accounts, Integer> {
    Optional<Accounts> findById(int id);

}

