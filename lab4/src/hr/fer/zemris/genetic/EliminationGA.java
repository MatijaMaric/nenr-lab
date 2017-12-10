package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.selection.ISelection;

import java.util.Arrays;
import java.util.Comparator;

public class EliminationGA extends GeneticAlgorithm {

    private int k = 3;

    public EliminationGA(IFitness fitness, ISelection selection, ICrossover crossover, IMutation mutation) {
        super(fitness, selection, crossover, mutation);
    }

    public EliminationGA(IFitness fitness, ISelection selection, ICrossover crossover, IMutation mutation, int k) {
        super(fitness, selection, crossover, mutation);
        this.k = k;
    }

    @Override
    public Population evolve(Population pop) {
        Population nextGeneration = pop.copy();

        Individual[] kTournament = new Individual[k];
        for (int j = 0; j < k; ++j) {
            kTournament[j] = getSelection().selection(pop);
        }
        Arrays.sort(kTournament, Comparator.comparingDouble(Individual::getFitness));
        Individual parent1 = kTournament[0];
        Individual parent2 = kTournament[1];
        Individual child = getCrossover().crossover(parent1, parent2);
        Individual mutant = getMutation().mutate(child);

        nextGeneration.removeIndividual(kTournament[2]);
        nextGeneration.addIndividual(mutant);

        return nextGeneration;
    }
}
