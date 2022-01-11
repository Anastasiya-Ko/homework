package com.example.pet.controllers;

import com.example.pet.entity.Test;
import com.example.pet.repository.TestRepository;
import com.example.pet.service.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/test/{id}")
    public Test readOne(@Validated @PathVariable("id") Integer id) {
        return testService.readOne(id);
    }

    @PutMapping("/test/{id}")
    public Test update(@PathVariable("id") Integer id, @RequestBody Test test) {

        return testService.update(test, id);
    }

    @DeleteMapping("/test/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        final boolean deleted = testService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED)
                : new ResponseEntity<>(HttpStatus.OK);
    }

}

