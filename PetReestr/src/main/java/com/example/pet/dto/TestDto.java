package com.example.pet.dto;

import com.example.pet.entity.Referral;
import com.example.pet.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class TestDto {

    private  Integer testId;

    @NotNull(message = "У Теста должен быть результат")
    @Pattern(regexp = "[12]", message = "Результат должен быть: 1-обнаружено, 2-не обнаружено")
    private String result;

    private Service service;

    private Referral referralId;

    public TestDto() { }
}
