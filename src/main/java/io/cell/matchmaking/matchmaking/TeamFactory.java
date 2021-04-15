package io.cell.matchmaking.matchmaking;

import io.cell.matchmaking.model.RegistrationRecord;

import java.util.List;

public interface TeamFactory {

    void createTeam(List<RegistrationRecord> records);

}
