package org.example.service;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Add an employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    // Delete employee by ID
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    // Update employee details
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(id);
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            // Update fields of the existing employee
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setGraduationDate(updatedEmployee.getGraduationDate());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setExpertise(updatedEmployee.getExpertise());
            existingEmployee.setManager(updatedEmployee.getManager());
            existingEmployee.setTeam(updatedEmployee.getTeam());
            existingEmployee.setGrossSalary(updatedEmployee.getGrossSalary());

            // Save the updated employee to the database
            return employeeRepository.save(existingEmployee);
        } else {
            return null; // Employee not found
        }
    }
}
