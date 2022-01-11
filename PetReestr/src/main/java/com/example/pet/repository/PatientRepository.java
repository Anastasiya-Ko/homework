package com.example.pet.repository;

import com.example.pet.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Integer> {


    @Override
    Page<Patient> findAll(Pageable pageable);


    @Override
    Optional<Patient> findById(Integer integer);

    @Override
    void deleteById(Integer integer);



}
