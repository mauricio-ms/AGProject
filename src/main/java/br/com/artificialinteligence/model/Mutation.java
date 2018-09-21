package br.com.artificialinteligence.model;

import java.util.Random;
import java.util.stream.IntStream;

public final class Mutation {

    private final Subject subject;

    private Mutation(final Subject subject) {
        this.subject = subject;
    }

    public static Mutation of(final Subject subject) {
        return new Mutation(subject);
    }

    public String mutate() {
        final String genes = subject.getGenes();
        final Integer genesLength = subject.getLength();
        final Integer indexToMutate = new Random().nextInt(genesLength);
        return IntStream.range(0, genesLength)
                .mapToObj(index -> {
                    if (index == indexToMutate) {
                        final char toMutate = genes.charAt(new Random().nextInt(genesLength));
                        return toMutate == '1' ? '0' : '1';
                    }
                    return genes.charAt(index);
                }).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString(); // TODO CRIAR COLLECTOR
    }
}