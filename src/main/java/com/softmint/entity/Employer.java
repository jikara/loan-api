package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String registrationNumber;
    private String kraPin;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<EmployerUser> users = new ArrayList<>();
    @OneToMany(mappedBy = "employer")
    private List<Employee> employees = new ArrayList<>();

    public void setUsers(List<EmployerUser> users) {
        for (EmployerUser user : users) {
            user.setEmployer(this);
        }
        this.users = users;
    }
}
