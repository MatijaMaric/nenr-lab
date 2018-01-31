package hr.fer.zemris.genetic.demo;

import hr.fer.zemris.genetic.*;
import hr.fer.zemris.genetic.crossover.DiscreteRecombination;
import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.HWFunction;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.GaussMutation;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.mutation.SimpleUniformMutation;
import hr.fer.zemris.genetic.selection.ISelection;
import hr.fer.zemris.genetic.selection.RouletteSelection;

import java.io.IOException;
import java.nio.file.Paths;

public class EliminationDemo {

    public static final int MAX_ITER = 20000;

    public static void main(String[] args) throws IOException {
        boolean noise = true;

        IFitness fitness = !noise ? HWFunction.fromFile(Paths.get("D:\\FER\\zad4-dataset1.txt"))
                                  : HWFunction.fromFile(Paths.get("D:\\FER\\zad4-dataset2.txt"));
        ISelection selection = new RouletteSelection();
        ICrossover crossover = new DiscreteRecombination();
        IMutation mutation = new GaussMutation(0.05, -4,4);
        GeneticAlgorithm ga = new EliminationGA(fitness, selection, crossover, mutation);

        Population pop = new Population(200, 5, fitness);

        for (int i = 0; i < MAX_ITER; ++i) {
            pop = ga.evolve(pop);
            Individual best = pop.getBestIndividual();
            if ((i+1) % 1000 == 0) {
                System.out.println((i+1) + "#: err=" + best.getFitness());
                System.out.println(best);
            }
        }

        //System.out.println(pop.getBestIndividual());
    }

}
