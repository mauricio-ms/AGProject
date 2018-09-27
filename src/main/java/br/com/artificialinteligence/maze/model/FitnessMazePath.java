package br.com.artificialinteligence.maze.model;

import br.com.artificialinteligence.maze.factories.DirectionsFactory;
import br.com.artificialinteligence.maze.repositories.MazeRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public final class FitnessMazePath {

    private final LoadingCache<String, Integer> cache = CacheBuilder
            .newBuilder()
            .build(CacheLoader.from(this::calculate));

    private Coordinates currentCoordinate;

    private Integer placesUsed;

    private Integer locksUsed;

    private Integer timesOut;

    private Integer goalDifference;

    private Integer timesRepeated;

    private static final FitnessMazePath INSTANCE = new FitnessMazePath();

    private FitnessMazePath() {
    }

    public static FitnessMazePath get() {
        return INSTANCE;
    }

    public Integer get(final String genes) {
//        try {
//            return cache.get(genes);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error to retrieve fitness createFrom cache");
//        }
        return calculate(genes);
    }

    private Integer calculate(final String genes) {
        currentCoordinate = Coordinates.of(-1, 0);
        placesUsed = 0;
        locksUsed = 0;
        goalDifference = 18;

        final List<Direction> path = DirectionsFactory.createFrom(genes);

        final List<DirectionCoordinates> directionsWithinTheLimits = path
                .stream()
                .map(d -> {
                    if( MazeRepository.get().hasLock(DirectionCoordinates.of(d, currentCoordinate)) ) {
                        locksUsed++;
                    }

                    move(d);

                    return DirectionCoordinates.of(d, currentCoordinate);
                })
                .filter(any -> !(currentCoordinate.getX() < 0 || currentCoordinate.getY() < 0 ||
                        currentCoordinate.getX() > 9 || currentCoordinate.getY() > 9))
                .collect(Collectors.toList());

        timesOut = path.size() - directionsWithinTheLimits.size();

        placesUsed = (int) directionsWithinTheLimits
                .stream()
                .map(DirectionCoordinates::getCoordinates)
                .distinct()
                .count();

        timesRepeated = 27 - placesUsed;

        if (!directionsWithinTheLimits.isEmpty()) {
            goalDifference = 18 - directionsWithinTheLimits.get(directionsWithinTheLimits.size() - 1).getIndex();
        }

        return goalDifference + (locksUsed * 2) + (timesOut * 100) + (timesRepeated * 100);
    }

    private void move(final Direction direction) {
        currentCoordinate = direction.getNextCoordinatesFromCurrent(currentCoordinate);
    }
}