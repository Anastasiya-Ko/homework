package com.example.pet.service;

import com.example.pet.entity.Test;
import com.example.pet.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestServiceImp implements TestService{


    @Autowired
    TestRepository testRepository;

    @Override
    public Test create(Test test) {
       return testRepository.save(test);
    }

    @Override
    public Page<Test> readAll(Pageable pageable) {
        return testRepository.findAll(pageable);
    }

    @Override
    public Test readOne(Integer id) {
        Optional<Test> result = testRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Тест с ID "+id+" не найден");
        }
    }

    @Override
    public Test update(Test testRequest, Integer id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Тест с ID "+id+" не изменён"));

        test.setResult(testRequest.getResult());

        return testRepository.save(test);
    }


    @Override
    public boolean deleteById(Integer id) {
        testRepository.deleteById(id);
        return false;
    }
}
