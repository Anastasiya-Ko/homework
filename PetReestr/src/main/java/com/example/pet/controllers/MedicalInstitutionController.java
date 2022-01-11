package com.example.pet.controllers;

import com.example.pet.service.MedicalInstitutionService;
import com.example.pet.entity.MedicalInstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalInstitutionController {

    @Autowired
    MedicalInstitutionService medicalInstitutionService;

    @GetMapping("/medical-institution")
    public ResponseEntity<List<MedicalInstitution>> readAll(@PageableDefault Pageable pageable) {
        Page<MedicalInstitution> pages = medicalInstitutionService.readAll(pageable);
        return ResponseEntity.ok().body(pages.getContent());
    }
}
