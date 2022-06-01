package com.onboarddok.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO {
    
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email Address is mandatory.")
    @Email(regexp = ".+[@].+[\\.].+", message = "Please provide a Valid Email Address.")
    private String emailAddress;

    @NotBlank(message = "Phone Number is mandatory")
    private String phoneNumber;
    
    private AddressRequestDTO addressRequestDTO;
}
