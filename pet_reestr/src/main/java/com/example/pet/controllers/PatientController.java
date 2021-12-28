package com.example.pet.controllers;


import com.example.pet.dto.PatientDto;
import com.example.pet.service.PatientService;
import com.example.pet.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/patient-dto/{id}")
    public ResponseEntity<PatientDto> readOneDto(@PathVariable(name = "id") Integer id) {
        Patient patient = patientService.readOne(id);

        // convert entity to DTO
        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return ResponseEntity.ok().body(patientResponse);
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Patient>> readAll(@PageableDefault Pageable pageable) {
        Page<Patient> pages = patientService.readAll(pageable);
        return ResponseEntity.ok().body(pages.getContent());
    }

    @GetMapping("/patient/{id}")
    public Patient readOne(@Validated @PathVariable("id") Integer id) {
        return patientService.readOne(id);
    }

    @PostMapping("/patient")
    public ResponseEntity<Patient> create(@Validated @RequestBody Patient patient) {
        patientService.create(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/patient-dto/{id}")
    public ResponseEntity<PatientDto> updateDto (@PathVariable("id") Integer id, @RequestBody PatientDto patientDto) {
        // convert DTO to Entity
        Patient patientRequest = modelMapper.map(patientDto, Patient.class);
        Patient patient = patientService.update(patientRequest, id);
        // entity to DTO
        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return ResponseEntity.ok().body(patientResponse);
    }

    @PutMapping("/patient/{id}")
    public Patient update(@PathVariable("id") Integer id, @RequestBody Patient patient) {

        return patientService.update(patient, id);
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        final boolean deleted = patientService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED)
                : new ResponseEntity<>(HttpStatus.OK);
    }
}






