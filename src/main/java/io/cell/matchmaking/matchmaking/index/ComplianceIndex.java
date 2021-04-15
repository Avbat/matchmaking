package io.cell.matchmaking.matchmaking.index;

public class ComplianceIndex {
    private Integer index;
    private Limit skillLimit;
    private Limit latencyLimit;

    public Limit getSkillLimit() {
        return skillLimit;
    }

    public ComplianceIndex setSkillLimit(Limit skillLimit) {
        this.skillLimit = skillLimit;
        return this;
    }

    public Limit getLatencyLimit() {
        return latencyLimit;
    }

    public ComplianceIndex setLatencyLimit(Limit latencyLimit) {
        this.latencyLimit = latencyLimit;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public ComplianceIndex setIndex(Integer index) {
        this.index = index;
        return this;
    }
}
