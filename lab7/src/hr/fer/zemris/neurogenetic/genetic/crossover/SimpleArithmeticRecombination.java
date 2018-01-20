package hr.fer.zemris.neurogenetic.genetic.crossover;

import java.util.Random;

public class SimpleArithmeticRecombination implements ICrossover {

    private Random random;

    public SimpleArithmeticRecombination(Random random) {
        this.random = random;
    }

    public SimpleArithmeticRecombination() {
        this(new Random());
    }

    @Override
    public double[] crossover(double[] a, double[] b) {
        double[] ans = new double[a.length];
        int pivot = random.nextInt(a.length);
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = i <= pivot ? a[i] : b[i];
        }
        return ans;
    }
}
