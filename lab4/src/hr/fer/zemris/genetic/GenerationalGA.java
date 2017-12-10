package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.selection.ISelection;

public class GenerationalGA extends GeneticAlgorithm {

    private boolean elitism;

    public GenerationalGA(IFitness fitness, ISelection selection, ICrossover crossover, IMutation mutation, boolean elitism) {
        super(fitness, selection, crossover, mutation);
        this.elitism = elitism;
    }

    @Override
    public Population evolve(Population pop) {
        Population nextGeneration = pop.blankCopy();

        if (elitism) {
            nextGeneration.addIndividual(pop.getBestIndividual());
        }

        int size = pop.getSize();
        size -= elitism ? 1 : 0;

        for (int i = 0; i < size; ++i) {
            Individual parent1 = getSelection().selection(pop);
            Individual parent2 = getSelection().selection(pop);
            Individual child = getCrossover().crossover(parent1, parent2);
            Individual mutant = getMutation().mutate(child);

            nextGeneration.addIndividual(mutant);
        }

        return nextGeneration;
    }
}
