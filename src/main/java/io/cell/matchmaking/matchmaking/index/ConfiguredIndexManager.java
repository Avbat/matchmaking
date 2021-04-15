package io.cell.matchmaking.matchmaking.index;

import io.cell.matchmaking.configs.MatchmakingConfiguration;
import io.cell.matchmaking.exceptions.LimitDefinitionException;
import io.cell.matchmaking.model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConfiguredIndexManager implements IndexManager {

    public static final Integer DEFAULT_INDEX = 666;
    private static final Logger LOG = LogManager.getLogger(ConfiguredIndexManager.class);
    private final Map<Limit, List<ComplianceIndex>> skillIndices = new HashMap<>();
    private final Map<Limit, List<ComplianceIndex>> latencyIndices = new HashMap<>();

    @Autowired
    public ConfiguredIndexManager(MatchmakingConfiguration configuration) {
        configuration.getComplianceIndices()
                .forEach(this::addIndices);
    }

    @Override
    public Integer getComplianceIndex(Participant participant) {
        try {
            Limit skillLimit = getSkillLimit(participant);
            Limit latencyLimit = getLatencyLimit(participant);
            Integer index = skillIndices.get(skillLimit).stream()
                    .filter(latencyIndices.get(latencyLimit)::contains)
                    .findAny()
                    .orElseThrow(LimitDefinitionException::new)
                    .getIndex();
            LOG.info("{} got index: {}", participant, index);
            return index;
        } catch (LimitDefinitionException e) {
            LOG.error("User got the default index. Participant: {}", participant, e);
            return DEFAULT_INDEX;
        }
    }

    private Limit getSkillLimit(Participant participant) throws LimitDefinitionException {
        return skillIndices.keySet().stream()
                .filter(limit ->
                        limit.getLowerBound() <= participant.getSkill()
                                && limit.getUpperBound() >= participant.getSkill())
                .findAny()
                .orElseThrow(() -> new LimitDefinitionException("Skill limit define failed"));
    }

    private Limit getLatencyLimit(Participant participant) throws LimitDefinitionException {
        return latencyIndices.keySet().stream()
                .filter(limit ->
                        limit.getLowerBound() <= participant.getLatency()
                                && limit.getUpperBound() >= participant.getLatency())
                .findAny()
                .orElseThrow(() -> new LimitDefinitionException("Latency limit define failed"));
    }

    private void addIndices(ComplianceIndex complianceIndex) {
        addSkillIdices(complianceIndex);
        addLatencyIndices(complianceIndex);
    }

    private void addLatencyIndices(ComplianceIndex complianceIndex) {
        Limit latencyLimit = complianceIndex.getLatencyLimit();
        if (latencyIndices.containsKey(latencyLimit)) {
            latencyIndices.get(latencyLimit).add(complianceIndex);
            return;
        }
        latencyIndices.put(latencyLimit, new ArrayList<>(Arrays.asList(complianceIndex)));
    }

    private void addSkillIdices(ComplianceIndex complianceIndex) {
        Limit skillLimit = complianceIndex.getSkillLimit();
        if (skillIndices.containsKey(skillLimit)) {
            skillIndices.get(skillLimit).add(complianceIndex);
            return;
        }
        skillIndices.put(skillLimit, new ArrayList<>(Arrays.asList(complianceIndex)));
    }

}
