package br.com.artificialinteligence.maze.factories;

import br.com.artificialinteligence.SplitEveryN;
import br.com.artificialinteligence.maze.model.Direction;

import java.util.List;
import java.util.stream.Collectors;

public final class DirectionsFactory {

    private static final int TO_SPLIT_GENES = 2;

    private DirectionsFactory() {
    }

    public static List<Direction> createFrom(final String genes) {
        return SplitEveryN.of(TO_SPLIT_GENES)
                .split(genes)
                .stream()
                .map(Direction::getByValue)
                .collect(Collectors.toList());
    }
}
