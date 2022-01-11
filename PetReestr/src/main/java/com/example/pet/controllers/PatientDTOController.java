package com.example.pet.controllers;


import com.example.pet.dto.PatientDto;
import com.example.pet.service.PatientService;
import com.example.pet.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
public class PatientDTOController {

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

    @PutMapping("/patient-dto/{id}")
    public ResponseEntity<PatientDto> updateDto (@PathVariable ("id") Integer id, @Valid @RequestBody PatientDto patientDto) {
        // convert DTO to Entity
        Patient patientRequest = modelMapper.map(patientDto, Patient.class);
        Patient patient = patientService.update(patientRequest, id);
        // entity to DTO
        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

        return ResponseEntity.ok().body(patientResponse);
    }


    @PostMapping("/patient-dto")
    public ResponseEntity<PatientDto> createDto(@Valid @RequestBody PatientDto patientDto){

        // convert PatientDto to Patient entity
        Patient patient = new Patient();
        patient.setSurname(patientDto.getSurname());
        patient.setName(patientDto.getName());
        patient.setPatronymic(patientDto.getPatronymic());
        patient.setBirthday(patientDto.getBirthday());
        patient.setSnils(patientDto.getSnils());
        patient.setGender(patientDto.getGender());

        Patient savedPatient = patientService.create(patient);

        // convert Patient entity to PatientDto class
        PatientDto patientResponse = new PatientDto();

        patientResponse.setSurname(savedPatient.getSurname());
        patientResponse.setName(savedPatient.getName());
        patientResponse.setPatronymic(savedPatient.getPatronymic());
        patientResponse.setBirthday(savedPatient.getBirthday());
        patientResponse.setSnils(savedPatient.getSnils());
        patientResponse.setGender(savedPatient.getGender());

        //        RequestResponse response = new RequestResponse();
//        response.setStatus(HttpStatus.OK);
//        response.setResponse(patientView.toString());

        return ResponseEntity.ok().body(patientResponse);
    }
}






