package hr.fer.zemris.genetic.demo;

import hr.fer.zemris.genetic.GenerationalGA;
import hr.fer.zemris.genetic.GeneticAlgorithm;
import hr.fer.zemris.genetic.Individual;
import hr.fer.zemris.genetic.Population;
import hr.fer.zemris.genetic.crossover.DiscreteRecombination;
import hr.fer.zemris.genetic.crossover.ICrossover;
import hr.fer.zemris.genetic.fitness.HWFunction;
import hr.fer.zemris.genetic.fitness.IFitness;
import hr.fer.zemris.genetic.mutation.GaussMutation;
import hr.fer.zemris.genetic.mutation.IMutation;
import hr.fer.zemris.genetic.selection.ISelection;
import hr.fer.zemris.genetic.selection.RouletteSelection;

import java.io.IOException;
import java.nio.file.Paths;

public class GenerationalDemo {

    public static void main(String[] args) throws IOException {
        boolean noise = true;

        IFitness fitness = !noise ? HWFunction.fromFile(Paths.get("D:\\FER\\zad4-dataset1.txt"))
                                  : HWFunction.fromFile(Paths.get("D:\\FER\\zad4-dataset2.txt"));
        ISelection selection = new RouletteSelection();
        ICrossover crossover = new DiscreteRecombination();
        IMutation mutation = new GaussMutation(0.1,-4,4);
        GeneticAlgorithm ga = new GenerationalGA(fitness, selection, crossover, mutation, true);

        Population pop = new Population(20, 5, fitness);

        for (int i = 0; i < 20000; ++i) {
            pop = ga.evolve(pop);
            Individual best = pop.getBestIndividual();
            if (i % 1000 == 0) System.out.println(best.getFitness());
        }

        Individual best = pop.getBestIndividual();
        for (int i = 0; i < best.getGenes().length; ++i) {
            System.out.print(best.getGenes()[i] + " ");
        }
        System.out.println();

    }
}
