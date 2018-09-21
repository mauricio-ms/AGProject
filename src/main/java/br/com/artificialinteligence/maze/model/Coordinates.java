package br.com.artificialinteligence.maze.model;

import java.util.Objects;

public final class Coordinates {

    private final Integer x;

    private final Integer y;

    private Coordinates(final Integer x,
                        final Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates of(final Integer x,
                                 final Integer y) {
        return new Coordinates(x, y);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}