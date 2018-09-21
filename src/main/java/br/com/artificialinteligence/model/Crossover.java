package br.com.artificialinteligence.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public final class Crossover {

    private final Subject fatherX;

    private final Subject fatherY;

    private Crossover(final Subject fatherX,
                      final Subject fatherY) {
        this.fatherX = fatherX;
        this.fatherY = fatherY;
    }

    public static Crossover of(final Subject fatherX,
                               final Subject fatherY) {
        return new Crossover(
                fatherX, fatherY
        );
    }

    public List<String> crossover() {
        return crossover(fatherX.getGenes(),
                fatherY.getGenes());
    }

    private List<String> crossover(final String fatherX,
                                   final String fatherY) {
        final int middleX = fatherX.length() / 2;
        final int boundX = middleX - 2;
        final Integer firstCutoff = new Random().nextInt(
                boundX
        ) + 1;
        final Integer lastCutoff = new Random().nextInt(
                boundX
        ) + middleX;
//        System.out.printf("Crossover: %s, %s\n", firstCutoff, lastCutoff);
        final List<String> strings = Arrays.asList(
                String.format("%s%s%s",
                        fatherX.substring(0, firstCutoff),
                        fatherY.substring(firstCutoff, lastCutoff),
                        fatherX.substring(lastCutoff)),
                String.format("%s%s%s",
                        fatherY.substring(0, firstCutoff),
                        fatherX.substring(firstCutoff, lastCutoff),
                        fatherY.substring(lastCutoff))
        );
//        System.out.println("Crossover Results:");
//        System.out.println(strings.get(0).length());
//        System.out.println(strings.get(1).length());
        return strings;

//        return Arrays.asList(
//                random(fatherX, fatherY),
//                random(fatherX, fatherY)
//        );
    }

    private String random(final String fatherX,
                          final String fatherY) {
        return IntStream.range(0, fatherX.length())
                .mapToObj(index -> {
                    final int selector = new Random().nextInt(2);
                    return selector == 1 ? fatherX.charAt(index) :
                            fatherY.charAt(index);
                }).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}