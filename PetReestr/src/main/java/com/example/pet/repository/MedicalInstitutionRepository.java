package com.example.pet.repository;

import com.example.pet.entity.MedicalInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalInstitutionRepository extends JpaRepository<MedicalInstitution, Integer> {

    @Override
    Page<MedicalInstitution> findAll(Pageable pageable);

    @Override
    Optional<MedicalInstitution> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
