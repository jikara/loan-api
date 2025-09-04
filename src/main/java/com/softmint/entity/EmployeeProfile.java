package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class EmployeeProfile extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Basic(optional = false)
    private String firstName;
    @Basic(optional = false)
    private String lastName;
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String idNumber;   // or government ID
    @Basic(optional = false)
    private String kraPin;   // or government ID
    private String phone;
    private String dateOfBirth;
    private String gender;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(mappedBy = "profile")
    private Employee employees;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}

