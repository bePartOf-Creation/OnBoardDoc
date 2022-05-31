package com.onboarddok.services;

import com.onboarddok.dtos.request.AddressRequestDTO;
import com.onboarddok.dtos.request.DoctorRequestDTO;
import com.onboarddok.models.Address;
import com.onboarddok.models.Doctor;
import com.onboarddok.repository.AddressRepository;
import com.onboarddok.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Doctor registerDoctor(DoctorRequestDTO doctorRequestDTO) {
        try {
            String duplicateDoctorRecord = checkIfDoctorExists(doctorRequestDTO);
            log.info("::::Doctor Record Payload is ---> {}", duplicateDoctorRecord);
        } catch (DataIntegrityViolationException e) {
            log.debug("::::: Duplicate Found ----> {}", e.getMessage());
            log.error("::::: Duplicate Found ----> {}", e.getMessage());
        }
        Doctor newDoctor = createNewDoctor(doctorRequestDTO);
        return this.doctorRepository.save(newDoctor);
    }

    @Override
    public Doctor editDoctorAddress(AddressRequestDTO addressRequestDTO, Long doctorId) {
        Doctor existingDoctor = this.doctorRepository.findById(doctorId).orElse(null);
        log.info("Current Doctor is ---> {}", existingDoctor);

        if (existingDoctor == null) {
            throw new IllegalArgumentException("Doctor Not Found");
        }

        this.modelMapper.map(addressRequestDTO, existingDoctor.getAddressId());
        Address newAddress = this.addressRepository.save(existingDoctor.getAddressId());
        existingDoctor.getAddressId().setHomeAddress(newAddress.getHomeAddress());
        return this.doctorRepository.save(existingDoctor);
    }

    @Override
    public Page<Doctor> fetchAllDoctor(int pageNumber, int pageSize) {
        log.info(":::PageSize --> {}", pageSize);
        log.info(":::PageNumber --> {}", pageNumber);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "lastName");

        return this.doctorRepository.findAll(pageable);
    }

    @Override
    public Doctor fetchADoctor(Long doctorId) {
        Doctor existingDoctor = this.doctorRepository.findById(doctorId).orElse(null);
        log.info("Current Doctor is ---> {}", existingDoctor);
        return existingDoctor;
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor existingDoctor = this.doctorRepository.findById(doctorId).orElse(null);
        log.info("Current Doctor is ---> {}", existingDoctor);

        if (existingDoctor == null) {
            throw new IllegalArgumentException("Doctor Not Found");
        }
        this.doctorRepository.delete(existingDoctor);
    }

    /**
     * Check if doctor exists string.
     *
     * @param doctorRequestDTO the doctor request dto
     * @return the string
     */
    public String checkIfDoctorExists(DoctorRequestDTO doctorRequestDTO) {
        boolean checkIfDoctorExists = this.doctorRepository.existsDoctorByEmailAddress(doctorRequestDTO.getEmailAddress());

        if (checkIfDoctorExists) {
            throw new DataIntegrityViolationException("Duplicate Doctor with the same Name Cannot be Created");
        }
        return "New Doctor Can Be Registered.";
    }

    private Doctor createNewDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = Doctor.builder()
                .firstName(doctorRequestDTO.getFirstName())
                .lastName(doctorRequestDTO.getLastName())
                .phoneNumber(doctorRequestDTO.getPhoneNumber())
                .emailAddress(doctorRequestDTO.getEmailAddress())
                .build();
        Doctor newDoctor = this.doctorRepository.save(doctor);
        Address houseAddress = Address.builder()
                .homeAddress(doctorRequestDTO.getAddressRequestDTO().getHomeAddress())
                .doctor(newDoctor)
                .build();
        Address newDoctorHouseAddress = addressRepository.save(houseAddress);
        newDoctor.setAddressId(newDoctorHouseAddress);
        return doctor;
    }
}
