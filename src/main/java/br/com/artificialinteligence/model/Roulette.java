package br.com.artificialinteligence.model;

import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Roulette {

    private static final Integer BETTER = 0;

    private static final Integer SECOND_BETTER = 1;

    private static final Integer SELECTED_LENGTH = 3;

    private final Population population;

    private final List<Subject> currentSubjects;

    private Roulette(final Population population,
                     final List<Subject> currentSubjects) {
        this.population = population;
        this.currentSubjects = currentSubjects;
    }

    public static Roulette of(final Population population) {
        return new Roulette(population, null);
    }

    public static Roulette of(final Population population,
                              final List<Subject> currentSubjects) {
        return new Roulette(population, currentSubjects);
    }

    public Parents spin() {
        final List<Subject> participants = currentSubjects == null ?
                select() : Lists.newArrayList(selectExclusives());

        return Parents.of(
                participants.get(BETTER),
                participants.get(SECOND_BETTER)
        );
    }

    private List<Subject> select() {
        return Stream.of(
                population.getRandom(),
                population.getRandom(),
                population.getRandom()
        ).sorted().collect(Collectors.toList());
    }

    private Set<Subject> selectExclusives() {
        final Set<Subject> exclusives = new HashSet<>();
        while( exclusives.size() < SELECTED_LENGTH) {
            final Subject randomSubject = population.getRandom();
            if( !currentSubjects.contains(randomSubject) ) {
                exclusives.add(randomSubject);
            }
        }
        return exclusives;
    }
}