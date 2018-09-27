package br.com.artificialinteligence.maze.repositories;

import br.com.artificialinteligence.maze.factories.LocksFactory;
import br.com.artificialinteligence.maze.model.DirectionCoordinates;
import br.com.artificialinteligence.maze.model.Maze;

public final class MazeRepository {

    private static final MazeRepository INSTANCE = new MazeRepository();

    private final Maze maze;

    private MazeRepository() {
        maze = Maze.of(LocksFactory.createDefault());
    }

    public static MazeRepository get() {
        return INSTANCE;
    }

    public Boolean hasLock(final DirectionCoordinates directionCoordinates) {
        return maze.hasLock(directionCoordinates);
    }
}
