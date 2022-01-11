package com.example.pet.entity;


import javax.persistence.*;


@Entity
@Table(name = "test")
public class Test {

    @Id
    @Column(name = "test_id")
    @SequenceGenerator(name = "testIdSeq", sequenceName = "test_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testIdSeq")
    private Integer testId;

    @Column(name = "result")
    private String result;


    @OneToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "referral_id")
    private Referral referralId;


    public Test(){}

    public Test(String result,Service service, Referral referralId) {
        this.result = result;
        this.service = service;
        this.referralId = referralId;
    }


    public Integer getTestId() {
        return testId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Referral getReferralId() {
        return referralId;
    }

    public void setReferralId(Referral referralId) {
        this.referralId = referralId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
