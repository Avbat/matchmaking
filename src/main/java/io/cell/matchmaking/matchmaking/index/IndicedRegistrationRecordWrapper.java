package io.cell.matchmaking.matchmaking.index;

import io.cell.matchmaking.model.RegistrationRecord;

public class IndicedRegistrationRecordWrapper implements Comparable<IndicedRegistrationRecordWrapper>{

    private RegistrationRecord record;
    private Integer index;

    public IndicedRegistrationRecordWrapper(RegistrationRecord record, Integer index) {
        this.record = record;
        this.index = index;
    }

    public RegistrationRecord getRecord() {
        return record;
    }

    public IndicedRegistrationRecordWrapper setRecord(RegistrationRecord record) {
        this.record = record;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public IndicedRegistrationRecordWrapper setIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * Сортируем:
     * 1. По индексу соответствия {@link ComplianceIndex}
     * 2. По снижению скила
     * 3. По увеличению пинга
     * @param o
     * @return
     */
    @Override
    public int compareTo(IndicedRegistrationRecordWrapper o) {
        if (this.index.equals(o.index)) {
            if (this.record.getParticipant().getSkill().equals(o.getRecord().getParticipant().getSkill())) {
                if (this.record.getParticipant().getLatency().equals(o.getRecord().getParticipant().getLatency())) {
                    return 0;
                }
                return  this.record.getParticipant().getLatency() - o.getRecord().getParticipant().getLatency() > 0 ? 1 : -1;
            }
            return  o.getRecord().getParticipant().getLatency() - this.record.getParticipant().getLatency() > 0 ? 1 : -1;
        }
        return this.index - o.index;
    }

    @Override
    public String toString() {
        return "IndicedRegistrationRecordWrapper{" +
                "record=" + record +
                ", index=" + index +
                '}';
    }
}
