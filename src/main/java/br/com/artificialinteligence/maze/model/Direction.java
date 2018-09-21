package br.com.artificialinteligence.maze.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public enum Direction {

    EAST("00") {
        @Override
        public Boolean isHorizontal() {
            return true;
        }

        @Override
        public Boolean isVertical() {
            return false;
        }

        @Override
        public Coordinates getNextCoordinatesFromCurrent(final Coordinates current) {
            return Coordinates.of(current.getX() - 1, current.getY());
        }

        @Override
        public Integer getReward() {
            return MazeConfig.SMALL_REWARD;
        }
    },
    NORTH("01") {
        @Override
        public Boolean isHorizontal() {
            return false;
        }

        @Override
        public Boolean isVertical() {
            return true;
        }

        @Override
        public Coordinates getNextCoordinatesFromCurrent(final Coordinates current) {
            return Coordinates.of(current.getX(), current.getY() + 1);
        }

        @Override
        public Integer getReward() {
            return MazeConfig.GREATER_REWARD;
        }
    },
    WEST("10") {
        @Override
        public Boolean isHorizontal() {
            return true;
        }

        @Override
        public Boolean isVertical() {
            return false;
        }

        @Override
        public Coordinates getNextCoordinatesFromCurrent(final Coordinates current) {
            return Coordinates.of(current.getX() + 1, current.getY());
        }

        @Override
        public Integer getReward() {
            return MazeConfig.GREATER_REWARD;
        }
    },
    SOUTH("11") {
        @Override
        public Boolean isHorizontal() {
            return false;
        }

        @Override
        public Boolean isVertical() {
            return true;
        }

        @Override
        public Coordinates getNextCoordinatesFromCurrent(final Coordinates current) {
            return Coordinates.of(current.getX(), current.getY() - 1);
        }

        @Override
        public Integer getReward() {
            return MazeConfig.SMALL_REWARD;
        }
    };

    private static final Map<Integer, Direction> BY_RANDOM_DIRECTIONS_MAP = buildByRandomDirectionsMap();

    private final String value;

    private Direction(final String value) {
        this.value = value;
    }

    private static Map<Integer, Direction> buildByRandomDirectionsMap() {
        final Map<Integer, Direction> directionMap = new HashMap<>();
        directionMap.put(0, Direction.EAST);
        directionMap.put(1, Direction.NORTH);
        directionMap.put(2, Direction.WEST);
        directionMap.put(3, Direction.SOUTH);
        return directionMap;
    }

    public static Direction getRandom() {
        final Integer randomPosition = new Random().nextInt(values().length);
        return BY_RANDOM_DIRECTIONS_MAP.get(randomPosition);
    }

    public static Direction getByValue(final String value) {
        return Stream.of(values())
                .filter(d -> d.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public String getValue() {
        return value;
    }

    public abstract Boolean isHorizontal();

    public abstract Boolean isVertical();

    public abstract Coordinates getNextCoordinatesFromCurrent(final Coordinates coordinates);

    public abstract Integer getReward();
}