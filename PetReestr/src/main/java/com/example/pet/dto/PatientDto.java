package com.example.pet.dto;

import com.example.pet.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientDto {

    private Integer patientId;

    @NotNull(message = "У пациента должна быть фамилия")
    private String surname;

    @NotNull(message = "У пациента должно быть имя")
    private String name;

    private String patronymic;

    @NotNull(message = "У пациента должна быть дата рождения")
    @PastOrPresent(message = "Дата рождения должна содержать прошедшую дату или сегодняшнее число")
    private LocalDate birthday;

    @NotNull(message = "У пациента должен быть СНИЛС")
    @Size(min = 6, max = 6, message = "Номер СНИЛСа должен содержать 6 символов")
    private String snils;

    private Gender gender;


    public PatientDto() {
    }
}
