package com.onboarddok.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
}
