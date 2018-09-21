package br.com.artificialinteligence.maze.model;

import java.util.List;

public final class Maze {

    private final List<Lock> locks;

    private MazePath mazePath;

    private Maze(final List<Lock> locks) {
        this.locks = locks;
    }

    public static Maze of(final List<Lock> locks) {
        return new Maze(locks);
    }

    public Boolean hasLock(final Coordinates currentCoordinate,
                           final Direction nextMove) {
        return locks
                .stream()
                .filter(l -> l.moveIsInLock(currentCoordinate, nextMove))
                .findFirst()
                .isPresent();
    }

    public List<Lock> getLocks() {
        return locks;
    }
}