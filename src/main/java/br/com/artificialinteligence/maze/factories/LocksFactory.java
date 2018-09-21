package br.com.artificialinteligence.maze.factories;

import br.com.artificialinteligence.maze.model.Lock;

import java.util.Arrays;
import java.util.List;

public final class LocksFactory {

    private LocksFactory() {
    }

    public static List<Lock> createDefault() {
        return Arrays.asList(
                Lock.createVertical(0, 0, 2),
                Lock.createVertical(0, 8, 9),
                Lock.createVertical(1, 1, 2),
                Lock.createVertical(1, 5, 6),
                Lock.createVertical(2, 3, 5),
                Lock.createVertical(3, 1, 2),
                Lock.createVertical(3, 3, 4),
                Lock.createVertical(3, 5, 7),
                Lock.createVertical(4, 0, 2),
                Lock.createVertical(4, 8, 10),
                Lock.createVertical(5, 1, 3),
                Lock.createVertical(5, 7, 9),
                Lock.createVertical(6, 2, 4),
                Lock.createVertical(6, 7, 8),
                Lock.createVertical(7, 6, 7),
                Lock.createVertical(7, 8, 9),
                Lock.createVertical(8, 5, 7),
                Lock.createVertical(8, 9, 10),
                Lock.createHorizontal(0, 2, 4),
                Lock.createHorizontal(0, 6, 9),
                Lock.createHorizontal(1, 2, 4),
                Lock.createHorizontal(1, 7, 9),
                Lock.createHorizontal(2, 1, 3),
                Lock.createHorizontal(2, 4, 5),
                Lock.createHorizontal(2, 8, 10),
                Lock.createHorizontal(3, 0, 2),
                Lock.createHorizontal(3, 4, 9),
                Lock.createHorizontal(4, 1, 2),
                Lock.createHorizontal(4, 5, 10),
                Lock.createHorizontal(5, 0, 3),
                Lock.createHorizontal(5, 4, 8),
                Lock.createHorizontal(6, 1, 5),
                Lock.createHorizontal(6, 6, 7),
                Lock.createHorizontal(6, 8, 9),
                Lock.createHorizontal(7, 1, 5),
                Lock.createHorizontal(7, 8, 10),
                Lock.createHorizontal(8, 1, 4),
                Lock.createHorizontal(8, 6, 8)
        );
    }
}
