package br.com.artificialinteligence.maze.model;

import br.com.artificialinteligence.SplitEveryN;
import br.com.artificialinteligence.maze.factories.DirectionsFactory;
import br.com.artificialinteligence.maze.factories.GenesFactory;
import br.com.artificialinteligence.model.Crossover;
import br.com.artificialinteligence.model.Mutation;
import br.com.artificialinteligence.model.Subject;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MazePath implements Subject {

    private final String genes;

    private final List<Direction> path;

    private final Integer fitness;

    private Coordinates currentCoordinate = Coordinates.of(-1, 0);

    private MazePath(final List<Direction> path) {
        this.path = path;
        genes = GenesFactory.createFrom(path);
        fitness = FitnessMazePath.get().get(genes);
    }

    public static MazePath of(final List<Direction> path) {
        return new MazePath(path);
    }

    public static MazePath of(final String genes) {
        return new MazePath(
                DirectionsFactory.createFrom(genes)
        );
    }

    public static MazePath from(final String genes,
                                final Double mutationRate) {
        if (new Random().nextDouble() <= mutationRate) {
            return MazePath.of(genes).mutate();
        }
        return MazePath.of(genes);
    }

    public static Subject createRandom(final Integer genesLength) {
        final List<Direction> genes = IntStream.range(0, genesLength)
                .mapToObj(index -> Direction.getRandom())
                .collect(Collectors.toList());
        return MazePath.of(genes);
    }

    public Boolean hasMove() {
        return !path.isEmpty();
    }

    public Coordinates move() {
        final Direction direction = path.remove(0);
        currentCoordinate = direction.getNextCoordinatesFromCurrent(currentCoordinate);
        return currentCoordinate;
    }

    @Override
    public String getGenes() {
        return path
                .stream()
                .map(Direction::getValue)
                .collect(Collectors.joining());
    }

    @Override
    public List<Subject> crossover(final Subject other) {
        return Crossover.of(this, other)
                .crossover()
                .stream()
                .map(MazePath::of)
                .collect(Collectors.toList());
    }

    @Override
    public MazePath mutate() {
        return MazePath.of(
                Mutation.of(this).mutate()
        );
    }

    @Override
    public Integer getFitness() {
        return fitness;
    }

    @Override
    public Integer getLength() {
        return getGenes().length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazePath mazePath = (MazePath) o;
        return Objects.equals(genes, mazePath.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }
}