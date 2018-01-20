package hr.fer.zemris.neurogenetic.genetic.crossover;

import java.util.Random;

public class DiscreteRecombination implements ICrossover {
    private Random random;

    public DiscreteRecombination(Random random) {
        this.random = random;
    }

    public DiscreteRecombination() {
        this(new Random());
    }

    @Override
    public double[] crossover(double[] a, double[] b) {
        double[] ans = new double[a.length];
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = random.nextBoolean() ? a[i] : b[i];
        }
        return ans;
    }
}
