package br.com.artificialinteligence.alphabet.factories;

import br.com.artificialinteligence.factories.PopulationFactory;
import br.com.artificialinteligence.model.IntegerChromosome;
import br.com.artificialinteligence.model.Subject;

import java.util.List;
import java.util.stream.Collectors;

public final class AlphabetPopulationFactory implements PopulationFactory {

    private final List<Character> solutionsDictionary;

    private AlphabetPopulationFactory() {
        solutionsDictionary = "!,.:;àáãâúíóôõéêQWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890 "
                .chars()
                .mapToObj(letter -> Character.valueOf((char) letter))
                .collect(Collectors.toList());
    }

    public static AlphabetPopulationFactory get() {
        return new AlphabetPopulationFactory();
    }

    @Override
    public Subject createRandomSubject(final Integer genesLength) {
        return IntegerChromosome.createRandom(genesLength, solutionsDictionary);
    }

    @Override
    public Subject createFromGenes(final String genes,
                                   final Double mutationRate) {
        return IntegerChromosome.from(genes, mutationRate);
    }
}