package com.example.pet.dto;

import com.example.pet.entity.Gender;
import lombok.Data;


@Data
public class PatientDto {

    private String surname;
    private String name;
    private String patronymic;
    private String snils;
    private Gender gender;


    public PatientDto() {
    }
}
