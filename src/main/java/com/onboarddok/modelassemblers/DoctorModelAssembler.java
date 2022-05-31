package com.onboarddok.modelassemblers;

import com.onboarddok.controller.DoctorController;
import com.onboarddok.dtos.response.DoctorResponseDTO;
import com.onboarddok.models.Doctor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DoctorModelAssembler extends RepresentationModelAssemblerSupport<Doctor, DoctorResponseDTO> {
    public DoctorModelAssembler() {
        super(DoctorController.class, DoctorResponseDTO.class);
    }

    @Override
    public DoctorResponseDTO toModel(Doctor doctorEntity) {
        DoctorResponseDTO doctorResponseDTO = instantiateModel(doctorEntity);
        return null;
    }
    
}
