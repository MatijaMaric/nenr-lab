package hr.fer.zemris.genetic.mutation;

import hr.fer.zemris.genetic.Individual;

import java.util.Random;

public class SimpleUniformMutation implements IMutation {

    private double lowerBound;
    private double upperBound;
    private double probability;
    private Random random;

    public SimpleUniformMutation(double probability, double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.probability = probability;
        random = new Random();
    }

    @Override
    public Individual mutate(Individual o) {
        Individual mutant = o.blankCopy();
        for (int i = 0; i < o.getGenes().length; ++i) {
            boolean mut = random.nextDouble() <= probability;
            if (mut) {
                double mutation = (upperBound - lowerBound) * random.nextDouble() + lowerBound;
                mutant.getGenes()[i] = mutation;
            } else {
                mutant.getGenes()[i] = o.getGenes()[i];
            }
        }
        return mutant;
    }
}
