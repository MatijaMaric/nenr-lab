package hr.fer.zemris.genetic.mutation;

import hr.fer.zemris.genetic.Individual;

public interface IMutation {

    Individual mutate(Individual o);
}
