package com.example.pet.service;

import com.example.pet.entity.MedicalInstitution;
import com.example.pet.repository.MedicalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalInstitutionServiceImp implements MedicalInstitutionService{

    @Autowired
    MedicalInstitutionRepository medicalInstitutionRepository;

    @Override
    public void create(MedicalInstitution medicalInstitution) {
        medicalInstitutionRepository.save(medicalInstitution);
    }

    @Override
    public Page<MedicalInstitution> readAll(Pageable pageable) {
       return medicalInstitutionRepository.findAll(pageable);
    }

    @Override
    public MedicalInstitution readOne(Integer id) {
        Optional<MedicalInstitution> result = medicalInstitutionRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Медицинская организация с ID "+id+" не найдена");
        }
    }

    @Override
    public MedicalInstitution update(MedicalInstitution medicalInstitutionRequest, Integer id) {
        MedicalInstitution medicalInstitution = medicalInstitutionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Медицинская организация с ID "+id+" не изменена"));

        medicalInstitution.setName(medicalInstitutionRequest.getName());
        medicalInstitution.setOgrn(medicalInstitutionRequest.getOgrn());
        medicalInstitution.setOid(medicalInstitutionRequest.getOid());

        return medicalInstitutionRepository.save(medicalInstitution);
    }

    @Override
    public boolean deleteById(Integer id) {
        medicalInstitutionRepository.deleteById(id);
        return false;
    }
}
