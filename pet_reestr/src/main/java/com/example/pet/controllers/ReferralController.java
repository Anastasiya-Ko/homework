package com.example.pet.controllers;

import com.example.pet.entity.Patient;
import com.example.pet.repository.ReferralRepository;
import com.example.pet.service.ReferralService;
import com.example.pet.entity.Referral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ReferralController {
    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    ReferralService referralService;

    @GetMapping("/referral")
    public ResponseEntity<List<Referral>>readAll(@PageableDefault Pageable pageable) {
        Page<Referral> pages = referralService.readAll(pageable);
        return ResponseEntity.ok().body(pages.getContent());
    }
    @GetMapping("/referral/{id}")
    public Referral readOne(@PathVariable("id") Integer id) {
        return referralService.readOne(id);
    }

    @PostMapping("/referral")
    public ResponseEntity<Referral> create(@RequestBody Referral referral) {
        referralService.create(referral);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
