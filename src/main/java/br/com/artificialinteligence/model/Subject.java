package br.com.artificialinteligence.model;

import java.util.List;

public interface Subject extends Comparable<Subject> {

    public abstract String getGenes();

    public abstract List<Subject> crossover(final Subject other);

    public abstract Subject mutate();

    public abstract Integer getFitness();

    public abstract Integer getLength();

    public default int compareTo(final Subject other) {
        return getFitness().compareTo(other.getFitness());
    }
}
