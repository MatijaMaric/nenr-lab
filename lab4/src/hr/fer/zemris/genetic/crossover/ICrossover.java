package hr.fer.zemris.genetic.crossover;

import hr.fer.zemris.genetic.Individual;

public interface ICrossover {
    Individual crossover(Individual a, Individual b);
}
