package br.com.artificialinteligence.maze.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Maze {

    private final Map<DirectionCoordinates, Boolean> locksByDirection;

    private Maze(final List<Lock> locks) {
        locksByDirection = createLocksByDirection(locks);
    }

    private Map<DirectionCoordinates, Boolean> createLocksByDirection(final List<Lock> locks) {
        final Map<DirectionCoordinates, Boolean> locksByDirection = new HashMap<>();
        for (Lock lock : locks) {
            if (lock.isVertical()) {
                for (int i = lock.getyStart(); i < lock.getyEnd(); i++) {
                    locksByDirection.put(DirectionCoordinates.of(Direction.WEST, Coordinates.of(lock.getxStart(), i)), true);
                    locksByDirection.put(DirectionCoordinates.of(Direction.EAST, Coordinates.of(lock.getxStart() + 1, i)), true);
                }
            } else {
                for (int i = lock.getxStart(); i < lock.getxEnd(); i++) {
                    locksByDirection.put(DirectionCoordinates.of(Direction.NORTH, Coordinates.of(i, lock.getyStart())), true);
                    locksByDirection.put(DirectionCoordinates.of(Direction.SOUTH, Coordinates.of(i, lock.getyStart() + 1)), true);
                }
            }
        }

        return locksByDirection;
    }

    public static Maze of(final List<Lock> locks) {
        return new Maze(locks);
    }

    public Boolean hasLock(final DirectionCoordinates directionCoordinates) {
        return locksByDirection.getOrDefault(directionCoordinates, false);
    }
}