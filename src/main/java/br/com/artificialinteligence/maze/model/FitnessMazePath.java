package br.com.artificialinteligence.maze.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class FitnessMazePath {

    private final Maze maze;

    private final MazePath mazePath;

    private Coordinates currentCoordinate = Coordinates.of(-1, 0);

    private Map<DirectionCoordinates2, Boolean> locks = new HashMap<>();

    private Integer placesUsed;

    private Integer locksUsed;

    private FitnessMazePath(final Maze maze,
                            final MazePath mazePath) {
        this.maze = maze;
        this.mazePath = mazePath;

        for (Lock lock : maze.getLocks()) {
            if (lock.isVertical()) {
                for (int i = lock.getyStart(); i < lock.getyEnd(); i++) {
                    locks.put(DirectionCoordinates2.of(Direction.WEST, Coordinates.of(lock.getxStart(), i)), true);
                    locks.put(DirectionCoordinates2.of(Direction.EAST, Coordinates.of(lock.getxStart() + 1, i)), true);
                }
            } else {
                for (int i = lock.getxStart(); i < lock.getxEnd(); i++) {
                    locks.put(DirectionCoordinates2.of(Direction.NORTH, Coordinates.of(i, lock.getyStart())), true);
                    locks.put(DirectionCoordinates2.of(Direction.SOUTH, Coordinates.of(i, lock.getyStart() + 1)), true);
                }
            }
        }
    }

    public static FitnessMazePath create(final Maze maze,
                                         final MazePath mazePath) {
        return new FitnessMazePath(maze, mazePath);
    }

    public Integer get() {
        currentCoordinate = Coordinates.of(-1, 0);
        final List<DirectionCoordinates> directionCoordinates = mazePath
                .getPath()
                .stream()
                .map(d -> {
                    move(d);
                    return DirectionCoordinates.of(d, currentCoordinate);
                })
                .filter(any -> !(currentCoordinate.getX() < 0 || currentCoordinate.getY() < 0 ||
                        currentCoordinate.getX() > 9 || currentCoordinate.getY() > 9))
                .collect(Collectors.toList());
        final List<DirectionCoordinates> distinct = directionCoordinates
                .stream()
                .distinct()
                .collect(Collectors.toList());

        currentCoordinate = Coordinates.of(-1, 0);
        final Integer locksPassed = mazePath
                .getPath()
                .stream()
                .filter(d -> {
//                    System.out.println(d);
                    final Boolean hasLock = locks.getOrDefault(DirectionCoordinates2.of(d, currentCoordinate), false);
//                    System.out.println(hasLock);
                    move(d);
                    return hasLock;
                })
                .collect(Collectors.toList())
                .size();

        currentCoordinate = Coordinates.of(-1, 0);
        timesOut = (int) mazePath
                .getPath()
                .stream()
                .map(d -> {
                    move(d);
                    return DirectionCoordinates.of(d, currentCoordinate);
                })
                .filter(any -> (currentCoordinate.getX() < 0 || currentCoordinate.getY() < 0 ||
                        currentCoordinate.getX() > 9 || currentCoordinate.getY() > 9))
                .count();
//        System.out.println("\n Distinct: " + distinct.size());
//        System.out.println("LocksPassed: " + locksPassed);
//        System.out.println();
        placesUsed = distinct.size();
        locksUsed = locksPassed;

        northCount = (int) distinct
                .stream()
                .map(DirectionCoordinates::getDirection)
                .filter(d -> Direction.NORTH.equals(d))
                .count();
        westCount = (int) distinct
                .stream()
                .map(DirectionCoordinates::getDirection)
                .filter(d -> Direction.WEST.equals(d))
                .count();
        southCount = (int) distinct
                .stream()
                .map(DirectionCoordinates::getDirection)
                .filter(d -> Direction.SOUTH.equals(d))
                .count();
        eastCount = (int) distinct
                .stream()
                .map(DirectionCoordinates::getDirection)
                .filter(d -> Direction.EAST.equals(d))
                .count();

        if (!directionCoordinates.isEmpty())
            difference = 18 - directionCoordinates.get(directionCoordinates.size() - 1).getIndex();

        repeated = 27 - placesUsed;
        return difference + (locksUsed * 2) + (timesOut * 100) + (repeated * 100);
    }

    int northCount;
    int westCount;
    int southCount;
    int eastCount;
    int timesOut;
    int difference;
    int repeated;

    private void move(final Direction direction) {
        currentCoordinate = direction.getNextCoordinatesFromCurrent(currentCoordinate);
    }

    @Override
    public String toString() {
        return "FitnessMazePath{" +
                "placesUsed=" + placesUsed +
                ", repeated=" + repeated +
                ", locksUsed=" + locksUsed +
                ", northCount=" + northCount +
                ", westCount=" + westCount +
                ", southCount=" + southCount +
                ", eastCount=" + eastCount +
                ", timesOut=" + timesOut +
                ", difference=" + difference +
                '}';
    }
}