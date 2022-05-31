package com.onboarddok.repository;

import com.onboarddok.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    /**
     * @param emailAddress .
     * @return true or false
     */
    boolean existsDoctorByEmailAddress(String emailAddress);
    
   
    Page<Doctor> findAll(Pageable pageable);

   
}
