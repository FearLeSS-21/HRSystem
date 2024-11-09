package org.example.service;

import org.example.model.Team;
import org.example.Repositry.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
