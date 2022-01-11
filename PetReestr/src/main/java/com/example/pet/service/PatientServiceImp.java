package com.example.pet.service;

import com.example.pet.repository.PatientRepository;
import com.example.pet.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImp implements PatientService {
    @Autowired
    PatientRepository patientRepository;


    @Override
    public Patient create(Patient patient) {
       return patientRepository.save(patient);
    }


    @Override
    public Page<Patient> readAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient readOne(Integer id) {
        Optional<Patient> result = patientRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Пациент с ID "+id+" не найден");
        }
    }

    @Override
    public Patient update(Patient patientRequest, Integer id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Пациент с ID "+id+" не изменён"));

        patient.setSurname(patientRequest.getSurname());
        patient.setName(patientRequest.getName());
        patient.setPatronymic(patientRequest.getPatronymic());
        patient.setBirthday(patientRequest.getBirthday());
        patient.setSnils(patientRequest.getSnils());
        patient.setGender(patientRequest.getGender());

        return patientRepository.save(patient);
    }


    @Override
    public boolean deleteById(Integer id) {
        patientRepository.deleteById(id);
        return false;
    }
}
