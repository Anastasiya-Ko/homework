package com.example.pet.repository;


import com.example.pet.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Integer> {

    @Override
    Page<Test> findAll(Pageable pageable);

    @Override
    Optional<Test> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
