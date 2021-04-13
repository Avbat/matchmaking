package io.cell.matchmaking.services;

import io.cell.matchmaking.model.Participant;

public interface ParticipantService {

    RegistrationResult register(Participant participant);
}
