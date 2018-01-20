package hr.fer.zemris.neurogenetic.genetic.mutation;

import java.util.Random;

public class ReplaceGaussMutation implements IMutation {

    private double pm;
    private Random random;
    private double sigma;

    public ReplaceGaussMutation(Random random, double sigma, double pm) {
        this.random = random;
        this.sigma = sigma;
        this.pm = pm;
    }

    public ReplaceGaussMutation(double sigma, double pm) {
        this(new Random(), sigma, pm);
    }

    @Override
    public double[] mutate(double[] individual) {
        double[] ans = new double[individual.length];
        for (int i = 0; i < ans.length; ++i) {
            if (random.nextDouble() < pm) {
                ans[i] = random.nextGaussian() * sigma;
            } else {
                ans[i] = individual[i];
            }
        }
        return ans;
    }
}
