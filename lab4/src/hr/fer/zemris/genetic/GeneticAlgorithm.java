package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.selection.ISelection;

public abstract class GeneticAlgorithm {

    private IFitness fitness;
    private ISelection selection;
    private ICrossover crossover;
    private IMutation mutation;

    public GeneticAlgorithm(IFitness fitness, ISelection selection, ICrossover crossover, IMutation mutation) {
        this.fitness = fitness;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    public IFitness getFitness() {
        return fitness;
    }

    public ISelection getSelection() {
        return selection;
    }

    public ICrossover getCrossover() {
        return crossover;
    }

    public IMutation getMutation() {
        return mutation;
    }

    public abstract Population evolve(Population pop);
}
