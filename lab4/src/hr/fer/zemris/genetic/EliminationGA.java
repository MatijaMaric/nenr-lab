package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.selection.ISelection;

import java.util.Arrays;
import java.util.Comparator;

public class EliminationGA extends GeneticAlgorithm {

    public EliminationGA(IFitness fitness, ISelection selection, ICrossover crossover, IMutation mutation) {
        super(fitness, selection, crossover, mutation);
    }

    @Override
    public Population evolve(Population pop) {
        Population nextGeneration = pop.copy();

        Individual[] kTournament = new Individual[3];
        for (int j = 0; j < 3; ++j) {
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
