package hr.fer.zemris.genetic.crossover;

import hr.fer.zemris.genetic.Individual;

import java.util.Random;

public class WholeArithmeticRecombination implements ICrossover {

    @Override
    public Individual crossover(Individual a, Individual b) {
        Individual child = a.blankCopy();
        for (int i = 0; i < child.getGenes().length; ++i) {
            child.getGenes()[i] = 0.5 * (a.getGenes()[i] + b.getGenes()[i]);
        }
        return child;
    }
}
