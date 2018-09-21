package br.com.artificialinteligence.model;

import org.apache.commons.lang3.BooleanUtils;

public final class ConfigAG {

    private static final Integer MAX_NUMBER_GENERATIONS_DEFAULT = 1000;

    private static final Integer POPULATION_SIZE_DEFAULT = 10000;

    private static final Double CROSSOVER_RATE_DEFAULT = 0.7;

    private static final Double MUTATION_RATE_DEFAULT = 0.3;

    private static final Double ELITISM_RATE_DEFAULT = 0.2;

    private static final Boolean ELITISM_DEFAULT = Boolean.TRUE;

    private final Integer maxNumberGenerations;

    private final Integer populationSize;

    private final Double crossoverRate;

    private final Double mutationRate;

    private final Double elitismRate;

    private final Boolean elitism;

    private ConfigAG(final Integer maxNumberGenerations,
                     final Integer populationSize,
                     final Double crossoverRate,
                     final Double mutationRate,
                     final Double elitismRate,
                     final Boolean elitism) {
        validate(maxNumberGenerations, populationSize, crossoverRate, mutationRate, elitismRate, elitism);

        this.maxNumberGenerations = maxNumberGenerations;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismRate = elitismRate;
        this.elitism = BooleanUtils.toBooleanDefaultIfNull(elitism, false);
    }

    private void validate(final Integer maxNumberGenerations,
                          final Integer populationSize,
                          final Double crossoverRate,
                          final Double mutationRate,
                          final Double elitismRate,
                          final Boolean elitism) {
        if (maxNumberGenerations == null) {
            throw new IllegalArgumentException("O 'Número de Gerações' é obrigatório.");
        }

        if (maxNumberGenerations <= 0) {
            throw new IllegalArgumentException("O 'Número de Gerações' deve ser maior que zero.");
        }

        if (populationSize == null) {
            throw new IllegalArgumentException("O 'Tamanho da População' é obrigatório.");
        }

        if (populationSize <= 0) {
            throw new IllegalArgumentException("O 'Tamanho da População' deve ser maior que zero.");
        }

        if (crossoverRate == null) {
            throw new IllegalArgumentException("A 'Taxa de Crossover' é obrigatória.");
        }

        if (crossoverRate < 0.0 || crossoverRate > 1.0) {
            throw new IllegalArgumentException("A 'Taxa de Crossover' deve ser um número entre 0 e 1.");
        }

        if (mutationRate == null) {
            throw new IllegalArgumentException("A 'Taxa de Mutação' é obrigatória.");
        }

        if (mutationRate < 0.0 || mutationRate > 1.0) {
            throw new IllegalArgumentException("A 'Taxa de Mutação' deve ser um número entre 0 e 1.");
        }

        if (elitism && elitismRate == null) {
            throw new IllegalArgumentException("Com Elitismo, a 'Taxa de Elitismo' é obrigatória.");
        }

        if (elitismRate < 0.0 || elitismRate > 1.0) {
            throw new IllegalArgumentException("A 'Taxa de Elitismo' deve ser um número entre 0 e 1.");
        }
    }

    public static ConfigAG of(final Integer maxNumberGenerations,
                              final Integer populationSize,
                              final Double crossoverRate,
                              final Double mutationRate,
                              final Double elitismRate,
                              final Boolean elitism) {
        return new ConfigAG(
                maxNumberGenerations, populationSize,
                crossoverRate, mutationRate,
                elitismRate, elitism
        );
    }

    public static ConfigAG createDefault() {
        return new ConfigAG(
                MAX_NUMBER_GENERATIONS_DEFAULT,
                POPULATION_SIZE_DEFAULT,
                CROSSOVER_RATE_DEFAULT,
                MUTATION_RATE_DEFAULT,
                ELITISM_RATE_DEFAULT,
                ELITISM_DEFAULT
        );
    }

    public Integer getMaxNumberGenerations() {
        return maxNumberGenerations;
    }

    public Integer getPopulationSize() {
        return populationSize;
    }

    public Double getCrossoverRate() {
        return crossoverRate;
    }

    public Double getMutationRate() {
        return mutationRate;
    }

    public Double getElitismRate() {
        return elitismRate;
    }

    public Boolean isElitism() {
        return Boolean.TRUE.equals(elitism);
    }

    @Override
    public String toString() {
        return "ConfigAG{" +
                "crossoverRate=" + crossoverRate +
                ", mutationRate=" + mutationRate +
                ", elitism=" + elitism +
                '}';
    }
}