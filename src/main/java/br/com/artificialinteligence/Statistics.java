package br.com.artificialinteligence;

import br.com.artificialinteligence.factories.AGFactory;
import br.com.artificialinteligence.model.ConfigAG;
import br.com.artificialinteligence.model.ProblemType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Statistics {

    private final Integer attempts;

    private List<Integer> fitnessResults;

    private Integer successAttempts;

    public Statistics(final Integer attempts) {
        this.attempts = attempts;
        fitnessResults = IntStream.range(0, attempts)
                .parallel()
                .mapToObj(any -> attempt())
                .collect(Collectors.toList());
        successAttempts = (int) fitnessResults
                .stream()
                .filter(f -> f.equals(0))
                .count();
    }

    private Integer attempt() {
        final ConfigAG configAG = ConfigAG.of(
                1000,
                3000,
                0.9,
                0.9,
                0.2,
                true
        );
        final AG ag = AGFactory.from(ProblemType.MAZE, configAG);

        while (!ag.reachToTheMaxNumberOfGenerations()) {
            ag.stage();
            if( ag.getBetter().getFitness().equals(0) ) {
                break;
            }
//            System.out.println("Geração: " + ag.getCurrentGeneration());
//            System.out.println("Fitness: " + ag.getBetter().getFitness());
        }

        System.out.println("Geração: " + ag.getCurrentGeneration());
        System.out.println("Fitness: " + ag.getBetter().getFitness());

        return ag.getBetter().getFitness();
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "attempts=" + attempts +
                ", fitnessResults=" + fitnessResults +
                ", successAttempts=" + successAttempts +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Running Statistics ...");
        final Statistics statistics = new Statistics(10);
        System.out.println(statistics);
    }
}