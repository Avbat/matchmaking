package io.cell.matchmaking.services;

import io.cell.matchmaking.model.Participant;
import io.cell.matchmaking.model.RegistrationRecord;

import java.util.List;

public interface RegistrationService {

    RegistrationResult register(Participant participant);

    List<RegistrationRecord> getWaitingParticipant();

    Long getCurrentTeam();

    void updateTeam(List<RegistrationRecord> records, Long team);
}
