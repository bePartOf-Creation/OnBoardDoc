package com.onboarddok.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id")
    @EqualsAndHashCode.Exclude
    @JsonBackReference // To prevent Infinite Recursion.
    private Address addressId;
}
