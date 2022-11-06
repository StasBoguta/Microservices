package com.mentor4you.repository;

import com.mentor4you.model.Cooperation;
import com.mentor4you.model.DTO.coopDTO.CoopStatus;
import com.mentor4you.model.Mentees;
import com.mentor4you.model.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface CooperationRepository extends JpaRepository<Cooperation,Integer> {


    @Query("SELECT c FROM Cooperation c WHERE c.mentors.id =:mentor and c.status =:status")
    Set<Cooperation> findByMentors(@Param("mentor") int mentor,@Param("status") CoopStatus status);

    @Query("SELECT c FROM Cooperation c WHERE c.mentees.id =:mentees and c.status IN :status")
    Set<Cooperation> findByMentees(@Param("mentees") int mentees,@Param("status") Set<CoopStatus> status);

    @Query("SELECT c FROM Cooperation c WHERE c.mentees.id =:mentee and c.mentors.id =:mentor")
    Cooperation  coopIsPresent(@Param("mentee") int mentee,@Param("mentor") int mentor);


    @Transactional
    @Modifying
    @Query("update Cooperation c set c.status =:status WHERE c.mentees =:mentee and c.mentors =:mentor")
    void approve(@Param("mentee") Mentees mentee,@Param("mentor") Mentors mentor,@Param("status") int status);

    /*@Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByUserStatusAndUserName(@Param("status") Integer userStatus,
                                         @Param("name") String userName);*/


}
