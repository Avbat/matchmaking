package io.cell.matchmaking.matchmaking.index;

public class Limit {
    private Double lowerBound;
    private Double upperBound;

    public Double getUpperBound() {
        return upperBound;
    }

    public Limit setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
        return this;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public Limit setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Limit limit = (Limit) o;

        if (!lowerBound.equals(limit.lowerBound)) return false;
        return upperBound.equals(limit.upperBound);
    }

    @Override
    public int hashCode() {
        int result = lowerBound.hashCode();
        result = 31 * result + upperBound.hashCode();
        return result;
    }
}
