package hr.fer.zemris.neurogenetic.genetic.mutation;

import java.util.Random;

public class AddGaussMutation implements IMutation {

    private double pm;
    private Random random;
    private double sigma;

    public AddGaussMutation(Random random, double sigma, double pm) {
        this.random = random;
        this.sigma = sigma;
        this.pm = pm;
    }

    public AddGaussMutation(double sigma, double pm) {
        this(new Random(), sigma, pm);
    }

    @Override
    public double[] mutate(double[] individual) {
        double[] ans = new double[individual.length];
        for (int i = 0; i < ans.length; ++i) {
            if (random.nextDouble() < pm) {
                ans[i] = individual[i] + random.nextGaussian() * sigma;
            } else {
                ans[i] = individual[i];
            }
        }
        return ans;
    }
}
