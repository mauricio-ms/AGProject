package br.com.artificialinteligence.parsers;

import java.util.Optional;
import java.util.logging.Logger;

public final class IntegerParser {

    private static final Logger LOGGER = Logger.getLogger(IntegerParser.class.getCanonicalName());

    private IntegerParser() {
    }

    public static Integer parse(final String value) {
        return Optional.ofNullable(value)
                .map(v -> {
                    try {
                        return Integer.valueOf(v);
                    } catch (NumberFormatException e) {
                        LOGGER.severe(String.format("Erro ao fazer o parse do valor '%s' para Long", v));
                        LOGGER.severe(e.getMessage());
                        return null;
                    }
                })
                .orElse(null);
    }
}