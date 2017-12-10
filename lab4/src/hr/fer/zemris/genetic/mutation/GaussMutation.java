package hr.fer.zemris.genetic.mutation;

import hr.fer.zemris.genetic.Individual;

import java.util.Random;

public class GaussMutation implements IMutation {

    private double probability;
    private double lowerBound;
    private double upperBound;
    private Random random;

    public GaussMutation(double probability, double lowerBound, double upperBound) {
        this.probability = probability;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.random = new Random();
    }

    @Override
    public Individual mutate(Individual o) {
        Individual mutant = o.blankCopy();
        for (int i = 0; i < o.getGenes().length; ++i) {
            boolean mut = random.nextDouble() <= probability;
            if (mut) {
                double mutation = random.nextGaussian();
                mutant.getGenes()[i] = o.getGenes()[i] + mutation;
                if (mutant.getGenes()[i] > upperBound) {
                    mutant.getGenes()[i] = upperBound;
                }
                if (mutant.getGenes()[i] < lowerBound) {
                    mutant.getGenes()[i] = lowerBound;
                }
            } else {
                mutant.getGenes()[i] = o.getGenes()[i];
            }
        }
        return mutant;
    }
}
