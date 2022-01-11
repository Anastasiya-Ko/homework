//package com.example.pet.service;
//
//import com.example.pet.dto.PatientDto;
//import org.springframework.stereotype.Service;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class PatientValidator implements ConstraintValidator<ValidatePatient, PatientDto> {
//
//    @Override
//    public boolean isValid(PatientDto dto, ConstraintValidatorContext constraintValidatorContext) {
//        if (dto.getSnils().contains("000000")) {
//            return false;
//        }
//        else return true;
//    }
//}
