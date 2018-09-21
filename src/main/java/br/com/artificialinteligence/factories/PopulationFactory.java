package br.com.artificialinteligence.factories;

import br.com.artificialinteligence.model.Parents;
import br.com.artificialinteligence.model.Population;
import br.com.artificialinteligence.model.Roulette;
import br.com.artificialinteligence.model.Subject;
import com.google.common.collect.Lists;

import java.util.*;

public interface PopulationFactory {

    public default Population create(final Integer genesLength,
                                     final Integer length) {
        final Set<Subject> subjects = new HashSet<>();
        while (subjects.size() < length) {
            final Subject randomSubject = createRandomSubject(genesLength);
            subjects.add(randomSubject);
        }

        return Population.of(Lists.newArrayList(subjects));
    }

    public default Population createWithElitism(final Population lastGeneration,
                                                final Double elitismRate,
                                                final Double crossoverRate,
                                                final Double mutationRate) {
        final Integer lastGenerationSize = lastGeneration.getSize();
        final List<Subject> subjects = lastGeneration.getBetters(elitismRate);
        while (subjects.size() < lastGenerationSize) {
            if (new Random().nextDouble() <= crossoverRate) {
                subjects.addAll(crossoverExclusive(lastGeneration, subjects));
//                int repeated = subjects.size() - (int) subjects
//                        .stream()
//                        .map(s -> s.getGenes())
//                        .distinct()
//                        .count();
//                if( repeated > 0 ) {
//                    System.out.println("CROSSOVER");
//                }
            } else {
                final Parents parents = Roulette.of(lastGeneration, subjects).spin();
                subjects.add(createFromGenes(
                        parents.getBetter().getGenes(), mutationRate));
                subjects.add(createFromGenes(
                        parents.getSecondBetter().getGenes(), mutationRate));
//                int repeated = subjects.size() - (int) subjects
//                        .stream()
//                        .map(s -> s.getGenes())
//                        .distinct()
//                        .count();
//                if( repeated > 0 ) {
//                    System.out.println("COPY");
//                }
            }
        }
        return Population.of(subjects);
    }

    public default List<Subject> crossoverExclusive(final Population lastGeneration,
                                                    final List<Subject> subjects) {
        final Parents parents = Roulette.of(lastGeneration).spin();
        final List<Subject> crossover = parents.crossover();
        final Boolean exists = subjects.contains(crossover.get(0)) ||
                subjects.contains(crossover.get(1));
        if( !exists ) {
            return crossover;
        }

        return crossoverExclusive(lastGeneration, subjects);
    }

    public default Population createWithoutElitism(final Population lastGeneration,
                                                   final Double crossoverRate,
                                                   final Double mutationRate) {
        final Integer lastGenerationSize = lastGeneration.getSize();
        final List<Subject> subjects = new ArrayList<>();
        while (subjects.size() < lastGenerationSize) {
            final Parents parents = Roulette.of(lastGeneration).spin();

            if (new Random().nextDouble() <= crossoverRate) {
                subjects.addAll(parents.crossover());
            } else {
                subjects.add(createFromGenes(
                        parents.getBetter().getGenes(), mutationRate));
                subjects.add(createFromGenes(
                        parents.getSecondBetter().getGenes(), mutationRate));
            }
        }
        return Population.of(subjects);
    }

    public abstract Subject createRandomSubject(final Integer genesLength);

    public abstract Subject createFromGenes(final String genes, final Double mutationRate);
}