package br.com.artificialinteligence.collectors;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class StringCollector implements
        Collector<Character, StringBuilder, String> {

    @Override
    public Supplier<StringBuilder> supplier() {
        return StringBuilder::new;
    }

    @Override
    public BiConsumer<StringBuilder, Character> accumulator() {
        return StringBuilder::appendCodePoint;
    }

    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return StringBuilder::append;
    }

    @Override
    public Function<StringBuilder, String> finisher() {
        return StringBuilder::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of(
                Characteristics.CONCURRENT,
                Collector.Characteristics.IDENTITY_FINISH);
    }
}