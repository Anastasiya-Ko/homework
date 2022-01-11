package com.example.pet.service;

import com.example.pet.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

        /**
         * Создает нового пациента
         * @param //patient - пациент для создания
         */
        Patient create(Patient patient);

        /**
         * Возвращает список всех имеющихся пациентов
         * @return список пациентов
         */
       Page<Patient> readAll(Pageable pageable);

        /**
         * Возвращает пациента по его ID
         * @param id - ID пациента
         * @return - объект пациента с заданным ID
         */
       Patient readOne(Integer id);

        /**
         * Обновляет пациента с заданным ID,
         * в соответствии с переданным пациентом
         * @param //patient - пациент в соответсвии с которым нужно обновить данные
         * @param id - id пациента, которого нужно обновить
         * @return - true если данные были обновлены, иначе false
         */
        Patient update(Patient patient, Integer id);
//        boolean update(Patient patient, Integer id);


        /**
         * Удаляет пациента с заданным ID
         * @param id - id пациента, которого нужно удалить
         * @return - true если пациент был удален, иначе false
         */
        boolean deleteById(Integer id);
    }

