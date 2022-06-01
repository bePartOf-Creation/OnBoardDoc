package com.onboarddok.builder;

import com.onboarddok.dtos.request.AddressRequestDTO;
import com.onboarddok.dtos.response.DoctorResponseDTO;
import com.onboarddok.models.Address;
import com.onboarddok.models.Doctor;

public class Utils {


    /**
     * Gets created doctor response dto.
     *
     * @param registerDoctor the register doctor
     * @return the created doctor response dto
     */
    public static DoctorResponseDTO getCreatedDoctorResponseDTO(Doctor registerDoctor) {
        return DoctorResponseDTO.builder()
                .id(registerDoctor.getId())
                .firstName(registerDoctor.getFirstName())
                .lastName(registerDoctor.getLastName())
                .emailAddress(registerDoctor.getEmailAddress())
                .phoneNumber(registerDoctor.getPhoneNumber())
                .addressId(registerDoctor.getAddressId())
                .build();
    }

    /**
     * Gets new doctor address.
     *
     * @param addressRequestDTO the address request dto
     * @return the new doctor address
     */
    public static Address getNewDoctorAddress(AddressRequestDTO addressRequestDTO) {
        return Address.builder()
                .homeAddress(addressRequestDTO.getHomeAddress())
                .build();
    }

}
