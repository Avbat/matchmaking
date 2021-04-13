package io.cell.matchmaking.services;

import io.cell.matchmaking.model.Participant;
import io.cell.matchmaking.model.RegistrationRecord;
import io.cell.matchmaking.repository.ParticipantRegistrationRepository;
import io.cell.matchmaking.utils.ParticipantVerifier;
import io.cell.matchmaking.utils.VerificationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.cell.matchmaking.services.RegistrationStatuses.*;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private static final Logger LOG = LogManager.getLogger(ParticipantService.class);
    private ParticipantRegistrationRepository registrationRepository;
    private ParticipantVerifier verifier;

    @Autowired
    public ParticipantServiceImpl(ParticipantRegistrationRepository registrationRepository,
                                  ParticipantVerifier verifier) {
        this.registrationRepository = registrationRepository;
        this.verifier = verifier;
    }

    @Override
    public RegistrationResult register(Participant participant) {
        VerificationResult verificationResult = verifier.verify(participant);
        if (!verificationResult.isSucces()) {
            String errorMessage = String.format("User registration failed. User: %s. Desc: %s",
                    participant,
                    verificationResult.getMessage());
            return createErrorResult(errorMessage);
        }
        return recording(participant);
    }

    private RegistrationResult recording(Participant participant) {
        try {
            if (checkRegistration(participant)) {
                return updateRecord(participant);
            }
            return makeRecord(participant);
        } catch (Exception e) {
            return createErrorResult("Current recording failed." + e.getMessage());
        }
    }

    private RegistrationResult makeRecord(Participant participant) {
        return createSuccessResult(
                registrationRepository.save(
                        new RegistrationRecord()
                                .setRecordId(UUID.randomUUID())
                                .setParticipant(participant)));
    }

    private RegistrationResult updateRecord(Participant participant) {
        RegistrationRecord currentRecord = registrationRepository.getByParticipantNameAndTeamIsNull(participant.getName());
        currentRecord.getParticipant().setLatency(participant.getLatency());
        currentRecord.getParticipant().setSkill(participant.getSkill());
        return createUpdateResult(registrationRepository.save(currentRecord));
    }

    private boolean checkRegistration(Participant participant) {
        return Optional.of(participant)
                .map(this::createRecordExample)
                .map(registrationRepository::exists)
                .orElse(false);
    }

    private Example<RegistrationRecord> createRecordExample(Participant participant) {
        Participant searchable = new Participant()
                .setName(participant.getName());
        return Example.of(new RegistrationRecord().setParticipant(searchable));
    }

    private RegistrationResult createUpdateResult(RegistrationRecord record) {
        LOG.info("Record of {} is updated.", record);
        String resultMessage = String.format("Participant %s is registered.", record.getParticipant().getName());
        return RegistrationResult.builder()
                .withMessage(resultMessage)
                .withStatus(UPDATED)
                .build();
    }

    private RegistrationResult createSuccessResult(RegistrationRecord record) {
        LOG.info("Record {} is registered.", record);
        String resultMessage = String.format("Participant %s is registered.", record.getParticipant().getName());
        return RegistrationResult.builder()
                .withMessage(resultMessage)
                .withStatus(SUCCESS)
                .build();
    }

    private RegistrationResult createErrorResult(String message) {
        LOG.warn(message);
        return RegistrationResult.builder()
                .withMessage(message)
                .withStatus(ERROR)
                .build();
    }
}
