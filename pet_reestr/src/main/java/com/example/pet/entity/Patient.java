package com.example.pet.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "patient")
public class Patient {
        @Id
        @Null
        @Column(name = "patient_id")
        @SequenceGenerator(name = "patientIdSeq", sequenceName = "patient_id_seq", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientIdSeq")
        private Integer patientId;

        @NotNull
        @Column(name = "surname", nullable = false)
        private String surname;

        @NotNull
        @Column(name = "name", nullable = false)
        private String name;

        @Null
        @Column(name = "patronymic")
        private String patronymic;

        @NotNull
        @Column(name = "birthday", nullable = false)
        private LocalDate birthday;

        @NotNull
        @Column(name = "snils", nullable = false)
        private String snils;

        @OneToOne
        @JoinColumn(name = "gender_id", nullable = false)
        private Gender gender;

    public Patient() {
    }

    public Patient(String surname, String name, String patronymic, LocalDate birthday, String snils, Gender gender) {

            this.surname = surname;
            this.name = name;
            this.patronymic = patronymic;
            this.birthday = birthday;
            this.snils = snils;
            this.gender = gender;
        }

        public Integer getPatientId() {
            return patientId;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public String getSnils() {
            return snils;
        }

        public void setSnils(String snils) {

            this.snils = snils;
        }

        @Override
        public String toString() {
            return "Patient{" +
                    "patientId=" + patientId +
                    ", surname='" + surname + '\'' +
                    ", name='" + name + '\'' +
                    ", patronymic='" + patronymic + '\'' +
                    ", birthday=" + birthday +
                    ", snils='" + snils + '\'' +
                    ", gender=" + gender +
                    '}';
        }

    }

