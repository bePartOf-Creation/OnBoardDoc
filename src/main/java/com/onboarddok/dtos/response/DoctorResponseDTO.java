package com.onboarddok.dtos.response;

import com.onboarddok.models.Address;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO extends RepresentationModel<DoctorResponseDTO> {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Address addressId;
}
