package com.example.pet.entity;

import javax.persistence.*;

@Entity
@Table(name = "medical_institution")
public class MedicalInstitution {

    @Id
    @Column(name = "medical_institution_id")
    @SequenceGenerator(name = "medicalInstitutionIdSeq", sequenceName = "medical_institution_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicalInstitutionIdSeq")
    private int medicalInstitutionId;

    @Column(name = "name")
    private String name;

    @Column(name = "ogrn")
    private int ogrn;

    @Column(name = "oid")
    private String oid;

    public MedicalInstitution(){
    }

    public MedicalInstitution(String name, int ogrn, String oid) {
        this.name = name;
        this.ogrn = ogrn;
        this.oid = oid;
    }

    public int getMedicalInstitutionId() {
        return medicalInstitutionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOgrn() {
        return ogrn;
    }

    public void setOgrn(int ogrn) {
        this.ogrn = ogrn;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
