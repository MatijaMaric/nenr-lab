package hr.fer.zemris.neurogenetic.genetic.crossover;

public class WholeArithmeticRecombination implements ICrossover {

    public WholeArithmeticRecombination() {
    }

    @Override
    public double[] crossover(double[] a, double[] b) {
        double[] ans = new double[a.length];
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = (a[i] + b[i]) / 2;
        }
        return ans;
    }
}
