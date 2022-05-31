package com.onboarddok.models;

import lombok.*;

import javax.persistence.*;


@ToString(exclude = {"addressId"})
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name="address_id")
    @EqualsAndHashCode.Exclude
    private Address addressId;
}
