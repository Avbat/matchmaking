package io.cell.matchmaking.utils;

import io.cell.matchmaking.model.Participant;

public interface ParticipantVerifier {
    VerificationResult verify(Participant participant);
}
