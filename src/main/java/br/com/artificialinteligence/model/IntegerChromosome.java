package br.com.artificialinteligence.model;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntegerChromosome implements Subject {

    private static final Random RANDOM = new Random();

    private final String genes;

    private final Integer fitness;

    private IntegerChromosome(final String genes) {
        this.genes = genes;
        fitness = calculateFitness();
    }

    public static IntegerChromosome of(final String genes) {
        return new IntegerChromosome(genes);
    }

    public static IntegerChromosome from(final String genes,
                                         final Double mutationRate) {
        final Random random = new Random();
        if (random.nextDouble() <= mutationRate) {
            return new IntegerChromosome(genes).mutate();
        }
        return new IntegerChromosome(genes);
    }

    public static IntegerChromosome createRandom(final Integer length,
                                                 final List<Character> solutionsDictionary) {
        final Random random = new Random();
        final String newRepresentation = IntStream.range(0, length)
                .mapToObj(index -> solutionsDictionary.get(random.nextInt(length)))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
        return new IntegerChromosome(newRepresentation);
    }

    public static IntegerChromosome createRandom(final String genes,
                                                 final List<Character> solutionsDictionary) {
        final int length = genes.length();
        final int randonPosition = RANDOM.nextInt(length);
        final String newRepresentation = IntStream.range(0, length)
                .mapToObj(index -> {
                    if (randonPosition == index) {
                        return solutionsDictionary.get(RANDOM.nextInt(length));
                    }
                    return genes.charAt(index);
                })
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
        return new IntegerChromosome(newRepresentation);
    }

    private Integer calculateFitness() {
        final String objective = CurrentProblem.getInstance().getObjective();
        return IntStream.range(0, objective.length())
                .map(index -> {
                    if (objective.charAt(index) == genes.charAt(index)) {
                        return 1;
                    }
                    return 0;
                })
                .sum();
    }

    @Override
    public String getGenes() {
        return genes;
    }

    @Override
    public List<Subject> crossover(final Subject other) {
        return Crossover.of(this, other)
                .crossover()
                .stream()
                .map(IntegerChromosome::of)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getLength() {
        return genes.length();
    }

    @Override
    public IntegerChromosome mutate() {
        return new IntegerChromosome(Mutation.of(this).mutate());
    }

    @Override
    public Integer getFitness() {
        return fitness;
    }
}
