package com.onboarddok.modelassemblers;

import com.onboarddok.builder.Utils;
import com.onboarddok.controller.DoctorController;
import com.onboarddok.dtos.request.DoctorRequestDTO;
import com.onboarddok.dtos.response.DoctorResponseDTO;
import com.onboarddok.models.Doctor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DoctorModelAssembler extends RepresentationModelAssemblerSupport<Doctor, DoctorResponseDTO> {
    public DoctorModelAssembler() {
        super(DoctorController.class, DoctorResponseDTO.class);
    }

    /**
     * This method perform Model Assembling operation.
     *
     * @param doctorEntity .
     * @return the DoctorResponse DTO.
     */
    @Override
    public DoctorResponseDTO toModel(Doctor doctorEntity) {
        DoctorResponseDTO doctorResponseDTO = Utils.getCreatedDoctorResponseDTO(doctorEntity);
        doctorResponseDTO.add(
                linkTo(methodOn(DoctorController.class).registerNewDoctor(new DoctorRequestDTO())).withRel("new_doctor"),
                linkTo(methodOn(DoctorController.class).updateDoctorAddress(new DoctorRequestDTO(), doctorEntity.getId())).withRel("update-doctor"),
                linkTo(methodOn(DoctorController.class).findADoctor(doctorEntity.getId())).withRel("doctor"),
                linkTo(methodOn(DoctorController.class).deleteADoctor(doctorEntity.getId())).withRel("remove-doctor")
        );

        return doctorResponseDTO;
    }

}
