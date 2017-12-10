package hr.fer.zemris.genetic.mutation;

import hr.fer.zemris.genetic.Individual;

import java.util.Random;

public class SimpleUniformMutation implements IMutation {

    private double lowerBound;
    private double upperBound;
    private Random random;

    public SimpleUniformMutation(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        random = new Random();
    }

    @Override
    public Individual mutate(Individual o) {
        Individual mutant = o.blankCopy();
        int m = random.nextInt(o.getGenes().length);
        double mutation = (upperBound - lowerBound) * random.nextDouble() + lowerBound;
        for (int i = 0; i < o.getGenes().length; ++i) {
            mutant.getGenes()[i] = m == i ? mutation : o.getGenes()[i];
        }
        return mutant;
    }
}
