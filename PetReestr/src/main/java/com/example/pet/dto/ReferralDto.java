package com.example.pet.dto;

import com.example.pet.entity.MedicalInstitution;
import com.example.pet.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralDto {
    private Integer referralId;

    @NotNull(message = "У Направления должна быть дата создания")
    @PastOrPresent(message = "Поле должно содержать прошедшую дату или сегодняшнее число")
    private LocalDateTime dateReferral;

    @NotNull(message = "У Направления должна быть дата результата")
    @Future(message = "Поле должно содержать дату в будущем")
    private LocalDateTime dateResult;

    @NotNull(message = "У Направления должен быть статус")
    @Pattern(regexp = "[12]", message = "Статус должен быть: 1 - направление создано, 2 – направление выполнено")
    private String status;

    private Patient patientId;
    private MedicalInstitution createdMuId;
    private MedicalInstitution executedMuId;

}
