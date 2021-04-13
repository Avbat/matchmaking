package io.cell.matchmaking.utils;

import io.cell.matchmaking.model.Participant;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParticipantVerifierImpl implements ParticipantVerifier {

    private static final String VERIFICATION_ERROR = "Verification failed.";
    private static final String VERIFICATION_SUCCESS = "Verification success.";

    @Override
    public VerificationResult verify(Participant participant) {
        return Optional.ofNullable(participant)
                .map(this::check)
                .orElseGet(this::createErrorResult);
    }

    private VerificationResult check(Participant participant) {
        VerificationResult result = new VerificationResult().setSucces(true);
        checkName(participant.getName(), result);
        checkLatency(participant.getLatency(), result);
        return result;
    }

    private void checkLatency(Double latency, VerificationResult result) {
        return;
    }


    private void checkName(String name, VerificationResult result) {
        return;
    }

    private VerificationResult createErrorResult() {
        return new VerificationResult()
                .setSucces(false)
                .setMessage(VERIFICATION_ERROR);
    }
}
