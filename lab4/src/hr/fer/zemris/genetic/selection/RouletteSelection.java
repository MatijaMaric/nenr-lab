package hr.fer.zemris.genetic.selection;

import hr.fer.zemris.genetic.Individual;
import hr.fer.zemris.genetic.Population;

import java.util.Random;

public class RouletteSelection implements ISelection {

    private Random random;

    public RouletteSelection() {
        random = new Random();
    }

    @Override
    public Individual selection(Population pop) {
        double sumFitness = pop.getIndividuals().stream().mapToDouble(Individual::getFitness).sum();
        double cur = random.nextDouble() * sumFitness;
        double i = 0;
        for (Individual select : pop.getIndividuals()) {
            i += select.getFitness();
            if (i >= cur) {
                return select;
            }
        }
        return null;
    }
}
