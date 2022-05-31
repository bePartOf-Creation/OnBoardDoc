package com.onboarddok.services;

import com.onboarddok.dtos.request.AddressRequestDTO;
import com.onboarddok.dtos.request.DoctorRequestDTO;
import com.onboarddok.models.Doctor;
import org.springframework.data.domain.Page;

public interface DoctorService {
    Doctor registerDoctor(DoctorRequestDTO doctorRequestDTO);
    Doctor editDoctorAddress(AddressRequestDTO addressRequestDTO,Long doctorId);
    Page<Doctor> fetchAllDoctor(int pageNumber, int pageSize);
    Doctor fetchADoctor(Long doctorId);
    void deleteDoctor(Long doctorId);
}
    
