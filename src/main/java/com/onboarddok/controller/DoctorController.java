package com.onboarddok.controller;

import com.onboarddok.builder.Utils;
import com.onboarddok.dtos.request.DoctorRequestDTO;
import com.onboarddok.dtos.response.DoctorResponseDTO;
import com.onboarddok.modelassemblers.DoctorModelAssembler;
import com.onboarddok.models.Doctor;
import com.onboarddok.services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private DoctorModelAssembler doctorModelAssembler;
    @Autowired
    private PagedResourcesAssembler<Doctor> pagedResourcesAssembler;

    /**
     * Register new doctor response entity.
     *
     * @param doctorRequestDTO the doctor request dto
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<?> registerNewDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        try {
            Doctor registeredDoctor = this.doctorService.registerDoctor(doctorRequestDTO);
            DoctorResponseDTO doctorResponseDTO = this.doctorModelAssembler.toModel(registeredDoctor);
            return new ResponseEntity<>(doctorResponseDTO, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Update doctor address response entity.
     *
     * @param doctorRequestDTO the doctor request dto
     * @param doctorId         the doctor id
     * @return the response entity
     */
    @PostMapping("/address/{doctorId}")
    public ResponseEntity<?> updateDoctorAddress(@RequestBody DoctorRequestDTO doctorRequestDTO, @PathVariable Long doctorId) {
        try {
            Doctor updatedHousedAddress = this.doctorService.editDoctorAddress(doctorRequestDTO, doctorId);
            DoctorResponseDTO doctorResponseDTO = Utils.getCreatedDoctorResponseDTO(updatedHousedAddress);
            return new ResponseEntity<>(doctorResponseDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find a doctor response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findADoctor(@PathVariable("id") Long id) {
        try {
            Doctor foundDoctor = this.doctorService.fetchADoctor(id);
            DoctorResponseDTO doctorResponseDTO = this.doctorModelAssembler.toModel(foundDoctor);
            return new ResponseEntity<>(doctorResponseDTO, HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find all doctors in pages response entity.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @return the response entity
     */
    @GetMapping()
    public ResponseEntity<PagedModel<DoctorResponseDTO>> findAllDoctorsInPages(
            @RequestParam(name = "pn") int pageNumber,
            @RequestParam(name = "ps") int pageSize) {
        Page<Doctor> allDoctorsInPages = this.doctorService.fetchAllDoctor(pageNumber, pageSize);
        PagedModel<DoctorResponseDTO> doctorModel = this.pagedResourcesAssembler.toModel(allDoctorsInPages, doctorModelAssembler);
        return new ResponseEntity<>(doctorModel, HttpStatus.FOUND);
    }

    /**
     * Delete a doctor response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteADoctor(@PathVariable("id") Long id) {
        try {
            this.doctorService.deleteDoctor(id);
            return new ResponseEntity<>("Doctor Deleted Successfully", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
