package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private LocalDate graduationDate;
    private String department;
    private Set<String> expertise;
    private String managerName;
    private String teamName;
    private double grossSalary;
    private double netSalary;

}
