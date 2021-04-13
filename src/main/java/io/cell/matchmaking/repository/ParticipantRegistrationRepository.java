package io.cell.matchmaking.repository;

import io.cell.matchmaking.model.RegistrationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantRegistrationRepository extends MongoRepository<RegistrationRecord, UUID> {
    RegistrationRecord getByParticipantNameAndTeamIsNull(String name);
}
