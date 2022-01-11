package com.example.pet.controllers;

import com.example.pet.dto.TestDto;
import com.example.pet.entity.Test;
import com.example.pet.repository.TestRepository;
import com.example.pet.service.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class TestDTOController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestService testService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping("/test-dto/{id}")
    public ResponseEntity<TestDto> readOneDto(@PathVariable(name = "id") Integer id) {

        Test test = testService.readOne(id);
        // convert entity to DTO
        TestDto testResponse = modelMapper.map(test, TestDto.class);

        return ResponseEntity.ok().body(testResponse);
    }


    @PutMapping("/test-dto/{id}")
    public ResponseEntity<TestDto> updateDto(@PathVariable("id") Integer id, @Valid @RequestBody TestDto testDto) {

        // convert DTO to Entity
        Test testRequest = modelMapper.map(testDto, Test.class);
        Test test = testService.update(testRequest, id);
        // entity to DTO
        TestDto testResponse = modelMapper.map(test, TestDto.class);
        return ResponseEntity.ok().body(testResponse);
    }

    @PostMapping("/test-dto")
    public ResponseEntity<TestDto> createDto(@Valid @RequestBody TestDto testDto) {

        // convert TestDto to Test entity
        Test test = new Test();
        test.setResult(testDto.getResult());
        test.setService(testDto.getService());
        test.setReferralId(testDto.getReferralId());

        Test savedTest = testService.create(test);

        // convert Test entity to TestDto class
        TestDto testResponse = new TestDto();

        testResponse.setResult(savedTest.getResult());
        testResponse.setService(savedTest.getService());
        testResponse.setReferralId(savedTest.getReferralId());

        return ResponseEntity.ok().body(testResponse);
    }
}


