package io.cell.matchmaking.matchmaking;

import io.cell.matchmaking.configs.MatchmakingConfiguration;
import io.cell.matchmaking.matchmaking.index.IndexManager;
import io.cell.matchmaking.matchmaking.index.IndicedRegistrationRecordWrapper;
import io.cell.matchmaking.model.RegistrationRecord;
import io.cell.matchmaking.repository.ParticipantRegistrationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ParticipantPool {

    private static final Logger LOG = LogManager.getLogger(ParticipantPool.class);

    private MatchmakingConfiguration configuration;
    private ParticipantRegistrationRepository registrationRepository;
    private TeamFactory teamFactory;
    private IndexManager indexManager;

    @Autowired
    public ParticipantPool(MatchmakingConfiguration configuration,
                           ParticipantRegistrationRepository registrationRepository,
                           TeamFactory teamFactory,
                           IndexManager indexManager) {
        this.configuration = configuration;
        this.registrationRepository = registrationRepository;
        this.teamFactory = teamFactory;
        this.indexManager = indexManager;
    }

    @Scheduled(fixedDelayString = "${pool-processing-delay}")
    public void process() {
        Set<IndicedRegistrationRecordWrapper> sortedRecords =
                registrationRepository.getAllByTeamIsNull().stream()
                .map(record ->
                        new IndicedRegistrationRecordWrapper(record, indexManager.getComplianceIndex(record.getParticipant())))
                .collect(Collectors.toCollection(TreeSet::new));
        LOG.info("The pool received {} participants.", sortedRecords.size());
        splitTeams(sortedRecords);
    }

    private void splitTeams(Set<IndicedRegistrationRecordWrapper> sortedRecords) {
        AtomicInteger index = new AtomicInteger(0);
        sortedRecords.stream()
                .collect(Collectors.groupingBy(x -> index.getAndIncrement() / configuration.getGroupSize()))
                .values().stream()
                .filter(indicedRegistrationRecordWrappers -> indicedRegistrationRecordWrappers.size() == configuration.getGroupSize())
                .map(ArrayList::new)
                .map(this::mapWrappersToRecords)
                .forEach(teamFactory::createTeam);
    }

    private List<RegistrationRecord> mapWrappersToRecords(List<IndicedRegistrationRecordWrapper> sortedRecords) {
        return sortedRecords.stream()
                .map(IndicedRegistrationRecordWrapper::getRecord)
                .collect(Collectors.toList());
    }
}
