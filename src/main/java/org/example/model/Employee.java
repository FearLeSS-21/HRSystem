package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private LocalDate graduationDate;
    private String department;
    private double grossSalary;

    @ElementCollection
    @CollectionTable(name = "expertise", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "expertise")
    private Set<String> expertise;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "manager")
    private Set<Employee> subordinates;

    public double getNetSalary() {
        double taxAmount = grossSalary * 0.15;
        double insuranceAmount = 500;
        return grossSalary - taxAmount - insuranceAmount;
    }


}
