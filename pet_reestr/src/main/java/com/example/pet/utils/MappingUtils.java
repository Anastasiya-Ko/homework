package com.example.pet.utils;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MappingUtils {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(MappingUtils.class, args);
    }
//    // из entity в dto
//
//    public PatientDto mapToProductDto(Patient patient){
//
//        PatientDto dto = new PatientDto();
//        dto.setPatientId(patient.getPatientId());
//        dto.setSurname(patient.getSurname());
//        dto.setName(patient.getName());
//
//        return dto;
//    }
//    //из dto в entity
//    public Patient mapToProductEntity(PatientDto dto){
//
//        Patient patient = new Patient();
//
//        patient.setSurname(dto.getSurname());
//        patient.setName(dto.getName());
//
//
//        return patient;
//    }
}
