package org.example.controller;

import org.example.model.Team;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Add a new Team
    @PostMapping("/addTeam")
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        try {
            Team savedTeam = teamService.addTeam(team);
            return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
