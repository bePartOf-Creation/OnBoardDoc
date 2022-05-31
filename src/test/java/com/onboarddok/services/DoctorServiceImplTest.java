package com.onboarddok.services;

import com.onboarddok.dtos.request.AddressRequestDTO;
import com.onboarddok.dtos.request.DoctorRequestDTO;
import com.onboarddok.models.Doctor;
import com.onboarddok.repository.DoctorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DoctorServiceImplTest {
    
   
    private final DoctorServiceImpl doctorService;
    
    @Autowired
    DoctorServiceImplTest(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
       
    }

    AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder().homeAddress("1, Sholebo Street, Sabo Yaba").build();
    DoctorRequestDTO doctorRequestDTO = DoctorRequestDTO.builder().firstName("lola").lastName("olasunkami")
                .emailAddress("lola.ola@gmail.com").phoneNumber("08076004528").addressRequestDTO(addressRequestDTO).build();

    @Test
    void registerDoctor() {
        Doctor newDoctor = this.doctorService.registerDoctor(doctorRequestDTO);
        assertThat(newDoctor.getEmailAddress()).isEqualTo("lola.ola@gmail.com");
    }

    @Test
    void editDoctorAddress() {
        Doctor newHouseAddress = this.doctorService.editDoctorAddress(addressRequestDTO, 2L);
        assertThat(newHouseAddress.getAddressId().getHomeAddress()).isEqualTo(addressRequestDTO.getHomeAddress());
    }

    @Test
    void fetchAllDoctor() {
        Page<Doctor> doctorPage = this.doctorService.fetchAllDoctor(0,5);
        Assertions.assertThat(doctorPage.getContent()).size().isEqualTo(5);
    }

    @Test
    void fetchADoctor() {
        Doctor existingDoctor = this.doctorService.fetchADoctor(6L);
        assertThat(existingDoctor.getEmailAddress()).isEqualTo("folashade.fowolabi@gmail.com");
        assertThat(existingDoctor.getFirstName()).isEqualTo("Folashade");
        assertThat(existingDoctor.getPhoneNumber()).isEqualTo("+445020689320");
    }

   
}
