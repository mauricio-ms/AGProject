package br.com.artificialinteligence.factories;

import br.com.artificialinteligence.alphabet.factories.AlphabetPopulationFactory;
import br.com.artificialinteligence.maze.factories.MazePopulationFactory;
import br.com.artificialinteligence.AG;
import br.com.artificialinteligence.model.ConfigAG;
import br.com.artificialinteligence.model.ProblemType;

public final class AGFactory {

    private AGFactory() {
    }

    public static AG from(final ProblemType problemType,
                          final ConfigAG configAG) {
        switch (problemType) {
            case ALPHABET:
                // TODO NAO FUNCIONA MAIS POR CAUSA DO OBJETIVO
                return new AG(AlphabetPopulationFactory.get(), configAG, 28, configAG.getPopulationSize());
            case MAZE:
                return new AG(MazePopulationFactory.get(), configAG, 27, configAG.getPopulationSize());
        }
        return null;
    }
}