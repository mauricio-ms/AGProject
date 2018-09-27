package br.com.artificialinteligence.statistics;

import br.com.artificialinteligence.AG;
import br.com.artificialinteligence.factories.AGFactory;
import br.com.artificialinteligence.model.ConfigAG;
import br.com.artificialinteligence.model.ProblemType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Statistics {

    private final List<Result> results;

    private final Integer successAttempts;

    public Statistics(final Integer attempts) {
        results = IntStream.range(0, attempts)
//                .parallel()
                .mapToObj(any -> attempt())
                .collect(Collectors.toList());
        successAttempts = (int) results
                .stream()
                .filter(Result::isSuccess)
                .count();
    }

    private Result attempt() {
        final LocalDateTime start = LocalDateTime.now();

        final ConfigAG configAG = ConfigAG.of(
                1000,
                35000,
                0.9,
                0.9,
                0.4,
                true
        );
        final AG ag = AGFactory.from(ProblemType.MAZE, configAG);

        while (!ag.reachToTheMaxNumberOfGenerations()) {
            ag.stage();
            if (ag.getBetter().getFitness().equals(0)) {
                break;
            }
//            System.out.println("Geração: " + ag.getCurrentGeneration());
//            System.out.println("Fitness: " + ag.getBetter().getFitness());
        }

        System.out.println("Time Spend: " + ChronoUnit.SECONDS.between(start, LocalDateTime.now()) + " seconds.");
        System.out.println("Geração: " + ag.getCurrentGeneration());
        System.out.println("Fitness: " + ag.getBetter().getFitness());
        System.out.println("Genes: " + ag.getBetter().getGenes());

        return new Result(ag.getBetter().getFitness(), ag.getCurrentGeneration(), ag.getBetter().getGenes());
    }

    public List<Result> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "results=" + results +
                ", successAttempts=" + successAttempts +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Running Statistics ...");
        final Statistics statistics = new Statistics(10);
        System.out.println(statistics);
        System.out.println("Final fitnesses:");
        System.out.println(statistics.getResults()
                .stream()
                .map(r -> String.valueOf(r.getFinalFitness()))
                .collect(Collectors.joining(", ")));
        System.out.println("Final generations:");
        System.out.println(statistics.getResults()
                .stream()
                .map(r -> String.valueOf(r.getFinalGeneration()))
                .collect(Collectors.joining(", ")));
        System.out.println("Final genes:");
        System.out.println(statistics.getResults()
                .stream()
                .map(r -> String.format("\"%s\"", r.getGenes()))
                .collect(Collectors.joining(", ")));

        System.out.println(LocalDateTime.now());

//        System.out.println(FitnessMazePath.get().get("100101101010010101010000000110101010100101101010111001"));
    }
}