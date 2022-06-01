package com.onboarddok.configs;

import com.onboarddok.models.Address;
import com.onboarddok.models.Doctor;
import com.onboarddok.repository.AddressRepository;
import com.onboarddok.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The type Database seeder.
 */
@Component
@Slf4j
public class DatabaseSeeder {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Seed database with doctor record at start up.
     */
    @PostConstruct
    public void seedDatabaseWithDoctorRecordAtStartUp() {

        try {
            Doctor doctor1 = Doctor.builder().firstName("Idowu").lastName("Faluade").emailAddress("idowu.faluade@gmail.com").phoneNumber("+445689322140").build();
            Doctor doctor2 = Doctor.builder().firstName("Tokunbo").lastName("Cole").emailAddress("tokunbo.cole@yahoo.com").phoneNumber("+2348156893220").build();
            Doctor doctor3 = Doctor.builder().firstName("Dennis").lastName("Ibori").emailAddress("ibori.fden@gmail.com").phoneNumber("+44509432689320").build();
            Doctor doctor4 = Doctor.builder().firstName("Mustapha").lastName("Olasunkanmi").emailAddress("musty.ola@outlook.com").phoneNumber("+445092689320").build();
            Doctor doctor5 = Doctor.builder().firstName("Seriki").lastName("Yekini").emailAddress("alhaji.seriki@gmail.com").phoneNumber("+44568032419320").build();
            Doctor doctor6 = Doctor.builder().firstName("Folashade").lastName("Owoloabi").emailAddress("folashade.fowolabi@gmail.com").phoneNumber("+445020689320").build();

            this.doctorRepository.saveAll(List.of(doctor1, doctor2, doctor3, doctor4, doctor5, doctor6));

            Address homeAddress1 = Address.builder().homeAddress("1, Tapa Street, Orile Iganmu. Lagos State").doctor(doctor1).build();
            Address homeAddress2 = Address.builder().homeAddress("3, Cole Street, NKaki. Garki Abuja").doctor(doctor2).build();
            Address homeAddress3 = Address.builder().homeAddress("6, Bello Street, Orile Iganmu. Lagos State").doctor(doctor3).build();
            Address homeAddress4 = Address.builder().homeAddress("16, Williams Street, GRA. Lagos State").doctor(doctor4).build();
            Address homeAddress5 = Address.builder().homeAddress("31, Babs Street, Anochie. Imo State").doctor(doctor5).build();
            Address homeAddress6 = Address.builder().homeAddress("Plot 213d Diamond Estate. Ogun State").doctor(doctor6).build();

            this.addressRepository.saveAll(List.of(homeAddress1, homeAddress2, homeAddress3, homeAddress4, homeAddress5, homeAddress6));

            doctor1.setAddressId(homeAddress1);
            doctor2.setAddressId(homeAddress2);
            doctor3.setAddressId(homeAddress3);
            doctor4.setAddressId(homeAddress4);
            doctor5.setAddressId(homeAddress5);
            doctor6.setAddressId(homeAddress6);

            this.doctorRepository.saveAll(List.of(doctor1, doctor2, doctor3, doctor4, doctor5, doctor6));
        } catch (DataIntegrityViolationException e) {
            log.debug("::::Duplicate  Found from Database Seeder Entity---> {}", e.getMessage());
            log.error("::::Duplicate  Found from Database Seeder Entity---> {}", e.getMessage());
        }

    }
}
