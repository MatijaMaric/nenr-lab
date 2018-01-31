package hr.fer.zemris.genetic;

import hr.fer.zemris.genetic.fitness.IFitness;

import java.util.Random;

public class Individual {
    private double[] genes;
    private double fitness;

    public Individual(double[] genes) {
        this.genes = genes;
    }

    public Individual(int size) {
        this.genes = new double[size];
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Individual blankCopy() {
        return new Individual(genes.length);
    }

    public static Individual generate(int genes, double lowerBound, double upperBound) {
        double[] generated = new double[genes];
        Random random = new Random();
        for (int i = 0; i < genes; ++i) {
            generated[i] = (upperBound - lowerBound) * random.nextDouble() + lowerBound;
        }
        return new Individual(generated);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; ++i) {
            sb.append(genes[i]);
            sb.append(" ");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
