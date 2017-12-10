package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.fitness.IFitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {
    private int size;
    private int genes;
    private List<Individual> individuals;
    private IFitness fitness;

    private double lowerBound = -4;
    private double upperBound = 4;

    public Population(int size, int genes, IFitness fitness, boolean clear) {
        this.size = size;
        this.genes = genes;
        this.fitness = fitness;
        this.individuals = new ArrayList<>(size);
        if (!clear) {
            for (int i = 0; i < size; ++i) {
                addIndividual(Individual.generate(genes, lowerBound, upperBound));
            }
        }
    }

    public Population(int size, int genes, IFitness fitness) {
        this(size, genes, fitness,false);
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public int getSize() {
        return size;
    }

    public int getGenes() {
        return genes;
    }

    public Population blankCopy() {
        return new Population(size, genes, fitness, true);
    }

    public void addIndividual(Individual individual) {
        if (individuals.size() == size) {
            throw new ArrayIndexOutOfBoundsException("Population is full");
        }
        individuals.add(individual);
        individual.setFitness(fitness.fitness(individual));
        sort();
    }

    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
    }

    public void sort() {
        individuals.sort(Comparator.comparingDouble(Individual::getFitness));
    }

    public Individual getIndividual(int i) {
        return individuals.get(i);
    }

    public Individual getBestIndividual() {
        return individuals.get(0);
    }

    public Population copy() {
        Population copyPop = new Population(size, genes, fitness);
        Collections.copy(copyPop.individuals, individuals);
        return copyPop;
    }
}
