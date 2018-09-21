package br.com.artificialinteligence.maze.model;

import java.util.Objects;

public final class DirectionCoordinates2 {

    private final Direction direction;

    private final Coordinates coordinates;

    private DirectionCoordinates2(final Direction direction,
                                  final Coordinates coordinates) {
        this.direction = direction;
        this.coordinates = coordinates;
    }

    public static DirectionCoordinates2 of(final Direction direction,
                                           final Coordinates coordinates) {
        return new DirectionCoordinates2(direction, coordinates);
    }

    public Integer getReward() {
        final Integer directionReward = direction.getReward();
//        if( coordinates.getX().equals(9) &&
//                coordinates.getY().equals(90)) {
//            return reward + 1000;
//        }
        Integer nearToTheObjectiveReward = 0;
//        if( Direction.NORTH.equals(direction) ) {
//            nearToTheObjectiveReward += 9 - (coordinates.getX() % 9);
//        } else if( Direction.WEST.equals(direction) ) {
//            nearToTheObjectiveReward += 9 - (coordinates.getY() % 9);
//        }

        return directionReward + nearToTheObjectiveReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionCoordinates2 that = (DirectionCoordinates2) o;
        return direction == that.direction &&
                Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {

        return Objects.hash(direction, coordinates);
    }

    @Override
    public String toString() {
        return "DirectionCoordinates{" +
                "direction=" + direction +
                ", coordinates=" + coordinates +
                '}';
    }
}