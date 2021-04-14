package io.cell.matchmaking.matchmaking;

import io.cell.matchmaking.model.Participant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

import static io.cell.matchmaking.matchmaking.ConfiguredIndexManager.DEFAULT_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConfiguredIndexManagerTest {

    @Autowired
    private ConfiguredIndexManager configuredIndexManager;

    @ParameterizedTest
    @MethodSource("provideInvalidParticipantFields")
    void getDefaultComplienceIndexWhenSkillOrLatencyOutOfBound(Double skill, Double latency) {
        String name = "toster";
        assertEquals(DEFAULT_INDEX, configuredIndexManager.getComplianceIndex(
                new Participant().setName(name)
                        .setSkill(skill)
                        .setLatency(latency)));
    }

    private static Stream<Arguments> provideInvalidParticipantFields() {
        Double normalSkill = 500d;
        Double normalLatency = 80d;
        Double lowInvalidSkill = -1d;
        Double highInvalidSkill = 100001d;
        Double lowInvalidLatency = -1d;
        Double highInvalidLatency = 100001d;
        return Stream.of(
                Arguments.of(normalSkill, lowInvalidLatency),
                Arguments.of(normalSkill, highInvalidLatency),
                Arguments.of(lowInvalidSkill, normalLatency),
                Arguments.of(highInvalidSkill, normalLatency)
        );
    }
}