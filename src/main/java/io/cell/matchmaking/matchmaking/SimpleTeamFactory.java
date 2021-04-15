package io.cell.matchmaking.matchmaking;

import io.cell.matchmaking.model.RegistrationRecord;
import io.cell.matchmaking.services.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class SimpleTeamFactory implements TeamFactory {

    private static final Logger LOG = LogManager.getLogger(SimpleTeamFactory.class);
    private RegistrationService registrationService;
    private AtomicLong lastTeamNumber;

    @Autowired
    public SimpleTeamFactory(RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.lastTeamNumber = new AtomicLong();
    }

    @PostConstruct
    public void init() {
        lastTeamNumber.set(registrationService.getCurrentTeam());
    }

    @Override
    public void createTeam(List<RegistrationRecord> records) {
        Long teamNumber = lastTeamNumber.incrementAndGet();
        registrationService.updateTeam(records, teamNumber);
        LOG.info(creatingMessage(records));
    }

    private String creatingMessage(List<RegistrationRecord> records) {
        return new StringBuilder()
                .append("Team: " + records.get(0).getTeam() + "\n")
                .append(createSkillMessage(records) + "\n")
                .append(createLatencyMessage(records) + "\n")
//                .append(createTimingMessage(records) + "\n") TODO
                .append(createParticipantNameMessage(records) + "\n")
                .toString();
    }

    private String createParticipantNameMessage(List<RegistrationRecord> records) {
        return records.stream()
                .map(record -> record.getParticipant().getName())
                .collect(Collectors.joining(", "));
    }

    private String createLatencyMessage(List<RegistrationRecord> records) {
        return String.format("Latency: %1$,.2f / %2$,.2f / %3$,.2f",
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getLatency())
                        .min().getAsDouble(),
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getLatency())
                        .average().getAsDouble(),
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getLatency())
                        .max().getAsDouble());
    }

    private String createSkillMessage(List<RegistrationRecord> records) {
        return String.format("Skills: %1$,.2f / %2$,.2f / %3$,.2f",
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getSkill())
                        .min().getAsDouble(),
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getSkill())
                        .average().getAsDouble(),
                records.stream()
                        .mapToDouble(record -> record.getParticipant().getSkill())
                        .max().getAsDouble());
    }
}
