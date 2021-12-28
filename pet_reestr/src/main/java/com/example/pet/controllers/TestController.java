package com.example.pet.controllers;

import com.example.pet.dto.TestDto;
import com.example.pet.repository.TestRepository;
import com.example.pet.service.TestService;
import com.example.pet.entity.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestService testService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/test")
    public ResponseEntity<List<Test>> readAll(@PageableDefault Pageable pageable) {
        Page<Test> pages = testService.readAll(pageable);
        return ResponseEntity.ok().body(pages.getContent());
    }

    @GetMapping("/test-dto/{id}")
    public ResponseEntity<TestDto> readOneDto(@PathVariable(name = "id") Integer id) {
        Test test = testService.readOne(id);
        // convert entity to DTO
        TestDto testResponse = modelMapper.map(test, TestDto.class);
        return ResponseEntity.ok().body(testResponse);
    }

    @GetMapping("/test/{id}")
    public Test readOne(@Validated @PathVariable("id") Integer id) {
        return testService.readOne(id);
    }

    @PutMapping("/test-dto/{id}")
    public ResponseEntity<TestDto> updateDto(@PathVariable("id") Integer id, @RequestBody TestDto testDto) {

        // convert DTO to Entity
        Test testRequest = modelMapper.map(testDto, Test.class);
        Test test = testService.update(testRequest, id);
        // entity to DTO
        TestDto testResponse = modelMapper.map(test, TestDto.class);
        return ResponseEntity.ok().body(testResponse);
    }

    @PutMapping("/test/{id}")
    public Test update(@PathVariable("id") Integer id, @RequestBody Test test) {

        return testService.update(test, id);
    }
}

