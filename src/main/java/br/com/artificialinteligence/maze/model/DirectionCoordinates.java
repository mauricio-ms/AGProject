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

    public Integer getIndex() {
        return coordinates.getX() + coordinates.getY();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionCoordinates that = (DirectionCoordinates) o;
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