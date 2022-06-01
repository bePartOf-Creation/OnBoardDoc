package com.onboarddok.services;

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

    /**
     * This method perform the registration operation.
     *
     * @param doctorRequestDTO .
     * @return the registered doctor.
     */
    @Override
    public Doctor registerDoctor(DoctorRequestDTO doctorRequestDTO) {
        boolean checkIfDoctorExists = this.doctorRepository.existsDoctorByEmailAddress(doctorRequestDTO.getEmailAddress());
        if (checkIfDoctorExists) {
            throw new DataIntegrityViolationException("Duplicate!!!!     Doctor already Exist");
        }
        Doctor newDoctor = createNewDoctor(doctorRequestDTO);
        return this.doctorRepository.save(newDoctor);
    }

    /**
     * This method performs update Doctor address operation.
     *
     * @param doctorRequestDTO .
     * @param doctorId         .
     * @return updated doctor with the new Address.
     */
    @Override
    public Doctor editDoctorAddress(DoctorRequestDTO doctorRequestDTO, Long doctorId) {
        Doctor existingDoctor = fetchADoctor(doctorId);
        log.info("Current Doctor is ---> {}", existingDoctor);
        log.info("Existing Doctor Address ---> {}", existingDoctor.getAddressId().getHomeAddress());

        this.modelMapper.map(doctorRequestDTO.getAddressRequestDTO(), existingDoctor.getAddressId());
        Address newAddress = this.addressRepository.save(existingDoctor.getAddressId());
        log.info("New address is ---> {}", newAddress.getHomeAddress());

        existingDoctor.getAddressId().setHomeAddress(newAddress.getHomeAddress());
        log.info("New Doctor Address ---> {}", existingDoctor.getAddressId().getHomeAddress());

        return this.doctorRepository.save(existingDoctor);
    }

    /**
     * This method performs all doctors operation.
     *
     * @param pageNumber .
     * @param pageSize   .
     * @return doctors in Pages
     */
    @Override
    public Page<Doctor> fetchAllDoctor(int pageNumber, int pageSize) {
        log.info(":::PageSize --> {}", pageSize);
        log.info(":::PageNumber --> {}", pageNumber);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "lastName");

        return this.doctorRepository.findAll(pageable);
    }

    /**
     * This method perform fetch  a doctor operation.
     *
     * @param doctorId .
     * @return a doctor .
     */
    @Override
    public Doctor fetchADoctor(Long doctorId) {
        Doctor existingDoctor = this.doctorRepository.findById(doctorId).orElse(null);
        if (existingDoctor == null) {
            throw new IllegalArgumentException("Doctor Not Found");
        }
        log.info("Current Doctor is ---> {}", existingDoctor);
        return existingDoctor;
    }

    /**
     * This method perform delete operation.
     *
     * @param doctorId .
     */
    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor existingDoctor = fetchADoctor(doctorId);
        log.info("Current Doctor is ---> {}", existingDoctor);

        this.doctorRepository.delete(existingDoctor);
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
        return newDoctor;
    }
}
