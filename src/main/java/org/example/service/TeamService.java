package org.example.service;

import org.example.model.Team;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // Add a new Team
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    // Get all Teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();  // This works because teamRepository is injected
    }
}
