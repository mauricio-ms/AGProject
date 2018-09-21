package br.com.artificialinteligence.model;

import java.util.List;

public final class Parents {

    private final Subject better;

    private final Subject secondBetter;

    private Parents(final Subject better,
                    final Subject secondBetter) {
        this.better = better;
        this.secondBetter = secondBetter;
    }

    public static Parents of(final Subject better,
                             final Subject secondBetter) {
        return new Parents(better, secondBetter);
    }

    public List<Subject> crossover() {
        return secondBetter.crossover(better);
    }

    public Subject getBetter() {
        return better;
    }

    public Subject getSecondBetter() {
        return secondBetter;
    }

    @Override
    public String toString() {
        return "Parents{" +
                "better=" + better +
                ", secondBetter=" + secondBetter +
                '}';
    }
}