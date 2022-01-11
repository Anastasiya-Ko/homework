package com.example.pet.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "referral")
public class Referral {

    @Id
    @Column(name = "referral_id")
    @SequenceGenerator(name = "referralIdSeq", sequenceName = "referral_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referralIdSeq")
    private Integer referralId;


    @Column(name = "date_referral")
    private LocalDateTime dateReferral;

    @Column(name = "date_result")
    private LocalDateTime dateResult;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "created_mu_id")
    private MedicalInstitution createdMuId;

    @ManyToOne
    @JoinColumn(name = "executed_mu_id")
    private MedicalInstitution executedMuId;
    

    public Referral(){}

    public Referral(Patient patientId) {
        this.patientId = patientId;
    }

    public Referral(MedicalInstitution createdMuId, MedicalInstitution executedMuId, Patient patientId, LocalDateTime dateReferral, LocalDateTime dateResult, String status) {
        this.createdMuId = createdMuId;
        this.executedMuId = executedMuId;
        this.patientId = patientId;
        this.dateReferral = dateReferral;
        this.dateResult = dateResult;
        this.status = status;
    }

    public Integer getReferralId() {
        return referralId;
    }

    public MedicalInstitution getCreatedMuId() {
        return createdMuId;
    }

    public void setCreatedMuId(MedicalInstitution createdMuId) {
        this.createdMuId = createdMuId;
    }

    public MedicalInstitution getExecutedMuId() {
        return executedMuId;
    }

    public void setExecutedMuId(MedicalInstitution executedMuId) {
        this.executedMuId = executedMuId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDateReferral() {
        return dateReferral;
    }

    public void setDateReferral(LocalDateTime dateReferral) {
        this.dateReferral = dateReferral;
    }

    public LocalDateTime getDateResult() {
        return dateResult;
    }

    public void setDateResult(LocalDateTime dateResult) {
        this.dateResult = dateResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
