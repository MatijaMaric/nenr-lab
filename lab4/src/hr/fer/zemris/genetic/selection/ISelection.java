package hr.fer.zemris.genetic.selection;

import hr.fer.zemris.genetic.Individual;
import hr.fer.zemris.genetic.Population;

public interface ISelection {
    Individual selection(Population pop);
}
