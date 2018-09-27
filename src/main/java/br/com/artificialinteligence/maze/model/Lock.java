package br.com.artificialinteligence.maze.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Lock {

    private final Integer xStart;

    private final Integer xEnd;

    private final Integer yStart;

    private final Integer yEnd;

    private Lock(final Integer xStart,
                final Integer xEnd,
                final Integer yStart,
                final Integer yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public static Lock createHorizontal(final Integer y,
                                        final Integer xStart,
                                        final Integer xEnd) {
        return new Lock(
                xStart, xEnd,
                y, y
        );
    }

    public static Lock createVertical(final Integer x,
                                      final Integer yStart,
                                      final Integer yEnd) {
        return new Lock(
                x, x,
                yStart, yEnd
        );
    }

    public Boolean isHorizontal() {
        return yStart.equals(yEnd);
    }

    public Boolean isVertical() {
        return xStart.equals(xEnd);
    }

    public Integer getxStart() {
        return xStart;
    }

    public Integer getxEnd() {
        return xEnd;
    }

    public Integer getyStart() {
        return yStart;
    }

    public Integer getyEnd() {
        return yEnd;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "xStart=" + xStart +
                ", xEnd=" + xEnd +
                ", yStart=" + yStart +
                ", yEnd=" + yEnd +
                '}';
    }
}