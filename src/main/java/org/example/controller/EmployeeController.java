package org.example.controller;

import org.example.dto.EmployeeDTO;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Add an employee
    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            EmployeeDTO employeeDTO = convertToDTO(savedEmployee);
            return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an employee by ID
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") int id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all employees
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            List<EmployeeDTO> employeeDTOs = employees.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get employee by ID
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") int id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                EmployeeDTO employeeDTO = convertToDTO(employee);
                return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update employee details
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            if (updatedEmployee != null) {
                EmployeeDTO employeeDTO = convertToDTO(updatedEmployee);
                return new ResponseEntity<>(employeeDTO, HttpStatus.OK);  // Successfully updated
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // Employee not found
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Error occurred
        }
    }

    // Convert Employee entity to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        String managerName = (employee.getManager() != null) ? employee.getManager().getName() : null;
        String teamName = (employee.getTeam() != null) ? employee.getTeam().getName() : null;

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getGraduationDate(),
                employee.getDepartment(),
                employee.getExpertise(),
                managerName,
                teamName,
                employee.getGrossSalary(),
                employee.getNetSalary()
        );
    }
}
