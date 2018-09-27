package br.com.artificialinteligence.model;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class Population {

    private static final Integer BETTER_SUBJECT_INDEX = 0;

    private final List<Subject> subjects;

    private Population(final List<Subject> subjects) {
        this.subjects = ListUtils.emptyIfNull(subjects);
        Collections.sort(this.subjects);
    }

    public static Population of(final List<Subject> subjects) {
        return new Population(subjects);
    }

    public Subject getRandom() {
        return getSubject(new Random().nextInt(getSize()));
    }

    public List<Subject> getBetters(final Double rate) {
        if( rate >= 1.0D ) {
            throw new IllegalArgumentException("Rate should be less than zero");
        }

        final int quantity = (int) (getSize() * rate);
        return new ArrayList<>(subjects.subList(0, quantity));
    }

    public Subject getBetter() {
        return getSubject(BETTER_SUBJECT_INDEX);
    }

    public Subject getSubject(final Integer index) {
        return subjects.get(index);
    }

    public Integer getSize() {
        return subjects.size();
    }

    public Boolean hasSolution(final String objective) {
        return subjects
                .stream()
                .filter(subject -> objective.equals(subject.getGenes()))
                .findFirst()
                .isPresent();
    }
}