package com.example.pet.controllers;

import com.example.pet.dto.ReferralDto;
import com.example.pet.entity.Referral;
import com.example.pet.service.ReferralService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class ReferralDTOController {

    @Autowired
    ReferralService referralService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/referral-dto/{id}")
    public ResponseEntity<ReferralDto> readOneDto(@PathVariable("id") Integer id) {
        Referral referral = referralService.readOne(id);
        ReferralDto referralResponse = modelMapper.map(referral, ReferralDto.class);
        return ResponseEntity.ok().body(referralResponse);
    }

    @PostMapping("/referral-dto")
    public ResponseEntity<ReferralDto> create(@Valid @RequestBody ReferralDto referralDto) {

        // convert PatientDto to Patient entity
        Referral referral = new Referral();
        referral.setDateReferral(referralDto.getDateReferral());
        referral.setDateResult(referralDto.getDateResult());
        referral.setStatus(referralDto.getStatus());
        referral.setPatientId(referralDto.getPatientId());
        referral.setCreatedMuId(referralDto.getCreatedMuId());
        referral.setExecutedMuId(referralDto.getExecutedMuId());

        Referral referralSaved = referralService.create(referral);

        // convert Patient entity to PatientDto class
        ReferralDto referralResponse = new ReferralDto();

        referralResponse.setDateReferral(referralSaved.getDateReferral());
        referralResponse.setDateResult(referralSaved.getDateResult());
        referralResponse.setStatus(referralSaved.getStatus());
        referralResponse.setPatientId(referralSaved.getPatientId());
        referralResponse.setCreatedMuId(referralSaved.getCreatedMuId());
        referralResponse.setExecutedMuId(referralSaved.getExecutedMuId());

        return ResponseEntity.ok().body(referralResponse);
    }
}
