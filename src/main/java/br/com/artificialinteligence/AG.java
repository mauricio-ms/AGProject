package br.com.artificialinteligence;

import br.com.artificialinteligence.factories.PopulationFactory;
import br.com.artificialinteligence.model.ConfigAG;
import br.com.artificialinteligence.model.Population;
import br.com.artificialinteligence.model.Subject;

public final class AG {

    private Population lastGeneration;

    private final ConfigAG configAG;

    private final PopulationFactory populationFactory;

    private Integer currentGeneration;

    public AG(final PopulationFactory populationFactory,
              final ConfigAG configAG,
              final Integer genesLength,
              final Integer populationSize) {
        this.populationFactory = populationFactory;
        lastGeneration = populationFactory.create(genesLength, populationSize);
        this.configAG = configAG;
        currentGeneration = 1;
    }

    public Subject stage() {
        lastGeneration = createNextGeneration();

        int repeated = lastGeneration.getSubjects().size() - (int) lastGeneration.getSubjects()
                .stream()
                .distinct()
                .count();

        System.out.println("Generation: " + currentGeneration);
        System.out.println("Fitness: " + getBetter().getFitness());
        System.out.println("Repeated: " + repeated);
        System.out.println();

        return lastGeneration.getBetter();
    }

    public Subject getBetter() {
        return lastGeneration.getBetter();
    }

    public Boolean reachToTheMaxNumberOfGenerations() {
        return currentGeneration > configAG.getMaxNumberGenerations();
    }

    private Population createNextGeneration() {
        currentGeneration++;
        if (configAG.isElitism()) {
            return populationFactory.createWithElitism(lastGeneration,
                    configAG.getElitismRate(), configAG.getCrossoverRate(), configAG.getMutationRate());
        }
        return populationFactory.createWithoutElitism(lastGeneration, configAG.getCrossoverRate(), configAG.getMutationRate());
    }

    public ConfigAG getConfigAG() {
        return configAG;
    }

    public Integer getCurrentGeneration() {
        return currentGeneration;
    }
}