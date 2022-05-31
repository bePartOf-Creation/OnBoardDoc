package com.onboarddok.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "doctors")
@Entity
@AllArgsConstructor
@NoArgsConstructor 
@Builder
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    
    @OneToOne(mappedBy = "doctor")
    private Address addressId;
}
