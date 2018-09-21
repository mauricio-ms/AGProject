package br.com.artificialinteligence.maze.model;

import br.com.artificialinteligence.SplitEveryN;
import br.com.artificialinteligence.model.Crossover;
import br.com.artificialinteligence.model.Mutation;
import br.com.artificialinteligence.model.Subject;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MazePath implements Subject {

    private static final int TO_SPLIT_GENES = 2;

    private final Maze maze;

    private final String genes;

    private final List<Direction> path;

    private final Integer fitness;

    private Coordinates currentCoordinate = Coordinates.of(-1, 0);

    private FitnessMazePath fitnessMazePath;

    private MazePath(final Maze maze,
                     final List<Direction> path) {
        this.maze = maze;
        this.path = path;
        genes = path
                .stream()
                .map(Direction::getValue)
                .collect(Collectors.joining());
        fitnessMazePath = FitnessMazePath.create(maze, this);
        fitness = fitnessMazePath.get();
    }

    public static MazePath of(final Maze maze,
                              final List<Direction> path) {
        return new MazePath(maze, path);
    }

    public static MazePath of(final Maze maze,
                              final String genes) {
        return new MazePath(
                maze,
                SplitEveryN.of(TO_SPLIT_GENES)
                        .split(genes)
                        .stream()
                        .map(Direction::getByValue)
                        .collect(Collectors.toList())
        );
    }

    public static MazePath from(final Maze maze,
                                final String genes,
                                final Double mutationRate) {
        if (new Random().nextDouble() <= mutationRate) {
            return MazePath.of(maze, genes).mutate();
        }
        return MazePath.of(maze, genes);
    }

    public static MazePath fixed(final Maze maze,
                                final String genes) {
        return MazePath.of(maze, genes);
    }

    public static Subject createRandom(final Integer genesLength,
                                       final Maze maze) {
        final List<Direction> genes = IntStream.range(0, genesLength)
                .mapToObj(index -> Direction.getRandom())
                .collect(Collectors.toList());
        return MazePath.of(maze, genes);
    }

    public Boolean hasMove() {
        return !path.isEmpty();
    }

    public Coordinates move() {
        final Direction direction = path.remove(0);
        currentCoordinate = direction.getNextCoordinatesFromCurrent(currentCoordinate);
        return currentCoordinate;
    }

    public Coordinates getCurrentCoordinate() {
        return currentCoordinate;
    }

    public List<Direction> getPath() {
        return path;
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
                .map(s -> MazePath.of(maze, s))
                .collect(Collectors.toList());
    }

    @Override
    public MazePath mutate() {
        return MazePath.of(
                maze,
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
    public String toString() {
        return fitnessMazePath.toString();
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