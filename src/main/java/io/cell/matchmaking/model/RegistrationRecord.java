package io.cell.matchmaking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.UUID;

@Document("registration")
public class RegistrationRecord {
    @Id
    private UUID recordId;
    private Participant participant;
    private ZonedDateTime recordTime;
    private Long team;

    public UUID getRecordId() {
        return recordId;
    }

    public RegistrationRecord setRecordId(UUID recordId) {
        this.recordId = recordId;
        return this;
    }

    public Participant getParticipant() {
        return participant;
    }

    public RegistrationRecord setParticipant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public ZonedDateTime getRecordTime() {
        return recordTime;
    }

    public RegistrationRecord setRecordTime(ZonedDateTime recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public Long getTeam() {
        return team;
    }

    public RegistrationRecord setTeam(Long team) {
        this.team = team;
        return this;
    }

    @Override
    public String toString() {
        return "RegistrationRecord{" +
                "recordId=" + recordId +
                ", participant=" + participant +
                ", recordTime=" + recordTime +
                ", team=" + team +
                '}';
    }
}
