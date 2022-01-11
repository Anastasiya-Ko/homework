package com.example.pet.entity;


import javax.persistence.*;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @Column(name = "service_id")
    @SequenceGenerator(name = "serviceIdSeq", sequenceName = "service_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serviceIdSeq")
    private Integer serviceId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    public Service(){
    }

    public Service(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
