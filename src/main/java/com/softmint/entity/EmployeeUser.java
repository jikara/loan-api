package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_users")
@PrimaryKeyJoinColumn(name = "user_id")
public class EmployeeUser extends User {
    @OneToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;



    public StaticRole getRole() {
        return (StaticRole) super.getRole();
    }

}
