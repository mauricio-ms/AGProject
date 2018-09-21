package br.com.artificialinteligence.maze.model;

import java.util.Objects;

public final class DirectionCoordinates {

    private final Direction direction;

    private final Coordinates coordinates;

    private DirectionCoordinates(final Direction direction,
                                 final Coordinates coordinates) {
        this.direction = direction;
        this.coordinates = coordinates;
    }

    public static DirectionCoordinates of(final Direction direction,
                                          final Coordinates coordinates) {
        return new DirectionCoordinates(direction, coordinates);
    }

    public Direction getDirection() {
        return direction;
    }

    public Integer getIndex() {
        return coordinates.getX() + coordinates.getY();
    }

    public Integer getReward() {
        final Integer directionReward = direction.getReward();
//        if( coordinates.getX().equals(9) &&
//                coordinates.getY().equals(90)) {
//            return reward + 1000;
//        }
        Integer nearToTheObjectiveReward = (9 - coordinates.getX()) + (9 - coordinates.getY());
//        if( Direction.SOUTH.equals(direction) ) {
//            nearToTheObjectiveReward += 9 - coordinates.getX();
//        } else if( Direction.EAST.equals(direction) ) {
//            nearToTheObjectiveReward += 9 - coordinates.getY();
//        }

//        if( coordinates.getX().equals(9) && coordinates.getY().equals(9) ) {
//            return directionReward + nearToTheObjectiveReward - 28;
//        }
        return directionReward + nearToTheObjectiveReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionCoordinates that = (DirectionCoordinates) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "DirectionCoordinates{" +
                "direction=" + direction +
                ", coordinates=" + coordinates +
                '}';
    }
}