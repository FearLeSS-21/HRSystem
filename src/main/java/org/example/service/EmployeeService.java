package org.example.service;

import org.example.Repositry.EmployeeRepository;
import org.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmployeeService {


    @Autowired
    private EmployeeRepository EmployeeRepository;

    public Employee addEmployee(Employee employee) {
        return EmployeeRepository.save(employee);
    }





}
