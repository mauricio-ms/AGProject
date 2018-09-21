package br.com.artificialinteligence.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PopulationTest {

    private Subject worse;

    private Subject better;

    @Before
    public void setUp() {
        worse = new Subject() {
            @Override
            public String getGenes() {
                return null;
            }

            @Override
            public List<Subject> crossover(Subject other) {
                return null;
            }

            @Override
            public Subject mutate() {
                return null;
            }

            @Override
            public Integer getFitness() {
                return 150;
            }

            @Override
            public Integer getLength() {
                return null;
            }
        };

        better = new Subject() {
            @Override
            public String getGenes() {
                return null;
            }

            @Override
            public List<Subject> crossover(Subject other) {
                return null;
            }

            @Override
            public Subject mutate() {
                return null;
            }

            @Override
            public Integer getFitness() {
                return 28;
            }

            @Override
            public Integer getLength() {
                return null;
            }
        };
    }

    @Test
    public void getBetterShouldReturnThatWithLowerFitnessValue() {
        final Subject betterOfPopulation = Population.of(Arrays.asList(worse, better))
                .getBetter();
        Assert.assertEquals(better, betterOfPopulation);
    }
}