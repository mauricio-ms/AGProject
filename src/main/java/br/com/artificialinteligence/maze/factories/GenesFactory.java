package br.com.artificialinteligence.maze.factories;

import br.com.artificialinteligence.maze.model.Direction;

import java.util.List;
import java.util.stream.Collectors;

public final class GenesFactory {

    private GenesFactory() {
    }

    public static String createFrom(final List<Direction> path) {
        return path
                .stream()
                .map(Direction::getValue)
                .collect(Collectors.joining());
    }
}
