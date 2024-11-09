package org.example.service;

import org.example.model.Employee;
import org.example.model.Team;
import org.example.Repositry.EmployeeRepository;
import org.example.Repositry.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TeamRepository teamRepository) {
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
    }


    public Employee addEmployee(Employee employee) {
        // If a team_id is provided, fetch the team and associate it with the employee
        if (employee.getTeam() != null && employee.getTeam().getId() != 0) {
            Team team = teamRepository.findById(employee.getTeam().getId()).orElse(null);
            if (team != null) {
                employee.setTeam(team);
            } else {
                throw new IllegalArgumentException("Team not found");
            }
        }

        return employeeRepository.save(employee);
    }
}
