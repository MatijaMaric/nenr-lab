package hr.fer.zemris.genetic.selection;

import hr.fer.zemris.genetic.Individual;
import hr.fer.zemris.genetic.Population;

import java.util.Random;

public class UniformSelection implements ISelection {

    private Random random;

    public UniformSelection() {
        random = new Random();
    }

    @Override
    public Individual selection(Population pop) {
        Random random = new Random();
        int i = random.nextInt(pop.getSize());
        return pop.getIndividual(i);
    }
}
