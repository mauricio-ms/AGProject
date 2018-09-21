package br.com.artificialinteligence.maze.factories;

import br.com.artificialinteligence.factories.PopulationFactory;
import br.com.artificialinteligence.maze.model.Maze;
import br.com.artificialinteligence.maze.model.MazePath;
import br.com.artificialinteligence.model.Subject;

public final class MazePopulationFactory implements PopulationFactory {

    private final Maze maze;

    private MazePopulationFactory() {
        maze = Maze.of(LocksFactory.createDefault());
    }

    public static MazePopulationFactory get() {
        return new MazePopulationFactory();
    }

    @Override
    public Subject createRandomSubject(final Integer genesLength) {
        return MazePath.createRandom(genesLength, maze);
    }

    @Override
    public Subject createFromGenes(final String genes,
                                   final Double mutationRate) {
        return MazePath.from(maze, genes, mutationRate);
    }
}