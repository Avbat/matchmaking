package io.cell.matchmaking.configs;

import io.cell.matchmaking.matchmaking.ComplianceIndex;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "matchmaking")
public class MatchmakingConfiguration {

    private List<ComplianceIndex> complianceIndices;

    public List<ComplianceIndex> getComplianceIndices() {
        return complianceIndices;
    }

    public MatchmakingConfiguration setComplianceIndices(List<ComplianceIndex> complianceIndices) {
        this.complianceIndices = complianceIndices;
        return this;
    }
}
