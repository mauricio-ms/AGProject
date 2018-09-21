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

    public Boolean moveIsInLock(final Coordinates currentCoordinates,
                                final Direction nextMove) {
        if( nextMove.isHorizontal() ) {
            final List<Coordinates> rangeCoordinates = IntStream.range(yStart, yEnd)
                    .mapToObj(y -> Coordinates.of(xStart, y + xStart))
                    .collect(Collectors.toList());
            return moveIsInLock(currentCoordinates, nextMove, rangeCoordinates);
        }
        final List<Coordinates> rangeCoordinates = IntStream.range(xStart, xEnd)
                .mapToObj(x -> Coordinates.of(x, yStart))
                .collect(Collectors.toList());
        return moveIsInLock(currentCoordinates, nextMove, rangeCoordinates);
    }

    private Boolean moveIsInLock(final Coordinates currentCoordinates,
                                 final Direction nextMove,
                                 final List<Coordinates> rangeCoordinates) {
        return rangeCoordinates
                .stream()
                .filter(v -> moveIsInLock(currentCoordinates, nextMove, v))
                .findFirst()
                .isPresent();
    }

    private Boolean moveIsInLock(final Coordinates currentCoordinates,
                                 final Direction nextMove,
                                 final Coordinates lockCoordinates) {
        final Coordinates nextCoordinates = nextMove.getNextCoordinatesFromCurrent(currentCoordinates);
        final Integer lockIndex = lockCoordinates.getX() + lockCoordinates.getY();
        final Integer sourceIndex = currentCoordinates.getX() + currentCoordinates.getY();
        final Integer targetIndex = nextCoordinates.getX() + nextCoordinates.getY();
        if( nextMove.isHorizontal() ) {
            final Boolean isInLock = sourceIndex >= 0 && sourceIndex <= lockIndex && lockIndex <= targetIndex;
            return lockCoordinates.getY().equals(currentCoordinates.getY()) &&
                    isInLock;
        } else if( nextMove.isVertical() ) {
            if( nextMove.equals(Direction.NORTH) ) {
                final Boolean isInLock = sourceIndex >= 0 &&
                        sourceIndex <= lockIndex && lockIndex < targetIndex;
                return lockCoordinates.getX().equals(currentCoordinates.getX()) &&
                        isInLock;
            }
            final Boolean isInLock = sourceIndex >= 0 &&
                    sourceIndex <= lockIndex && lockIndex <= targetIndex ||
                    targetIndex <= lockIndex && lockIndex <= sourceIndex;
            return lockCoordinates.getX().equals(currentCoordinates.getX()) &&
                    isInLock;
//            return lockCoordinates.equals(nextCoordinates);
        }
        return false;
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