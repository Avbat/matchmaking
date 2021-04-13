package io.cell.matchmaking.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Participant {

    private String name;
    private Double skill;
    private Double latency;

    public String getName() {
        return name;
    }

    public Participant setName(String name) {
        this.name = name;
        return this;
    }

    public Double getSkill() {
        return skill;
    }

    public Participant setSkill(Double skill) {
        this.skill = skill;
        return this;
    }

    public Double getLatency() {
        return latency;
    }

    public Participant setLatency(Double latency) {
        this.latency = latency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (!name.equals(that.name)) return false;
        if (!skill.equals(that.skill)) return false;
        return latency.equals(that.latency);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + skill.hashCode();
        result = 31 * result + latency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", skill=" + skill +
                ", latency=" + latency +
                '}';
    }
}
