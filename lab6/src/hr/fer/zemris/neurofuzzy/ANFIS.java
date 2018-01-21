package hr.fer.zemris.neurofuzzy;

import hr.fer.zemris.neurofuzzy.support.Rule;
import hr.fer.zemris.neurofuzzy.support.Sample;

import java.util.ArrayList;
import java.util.List;

public class ANFIS {

    public double learningRate;

    private List<Rule> rules;
    private List<Sample> samples;
    private int iters;

    private IFunction fx;

    private List<Double> errors;

    public ANFIS(double learningRate, int rulesCount, int iters) {
        this.learningRate = learningRate;
        this.iters = iters;
        this.rules = new ArrayList<>();
        for (int i = 0; i < rulesCount; ++i) {
            this.rules.add(new Rule());
        }
        samples = new ArrayList<>();
        this.fx = new HWFunction();
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                samples.add(new Sample(i, j, fx));
            }
        }
    }

    private double getSA(double x, double y) {
        double ans = 0;
        for (Rule rule : rules) {
            ans += rule.getT(x, y);
        }
        return ans;
    }

    private double getSAZ(double x, double y) {
        double ans = 0;
        for (Rule rule : rules) {
            ans += rule.getZ(x, y) * rule.getT(x, y);
        }
        return ans;
    }

    private double getO(double x, double y) {
        return getSAZ(x, y) / getSA(x, y);
    }

    public double predict(double x, double y) {
        return getO(x, y);
    }

    private double mse() {
        double ans = 0;
        for (Sample sample : samples) {
            ans += Math.pow(getO(sample.x, sample.y) - sample.z, 2);
        }
        return ans / (2*samples.size());
    }

    private void addError(Sample sample) {
        double sA = getSA(sample.x, sample.y);
        double sAZ = getSAZ(sample.x, sample.y);
        double o = sAZ / sA;

        for (Rule rule : rules) {
            rule.addError(sample, o, sA, sAZ);
        }
    }

    private void updateRules() {
        for (Rule rule : rules) {
            rule.update(learningRate);
        }
    }

    public void fit(boolean stochastic, boolean debug) {
        errors = new ArrayList<>();
        for (int i = 0; i < iters; ++i) {
            if (stochastic) {
                Sample sample = samples.get(i % samples.size());
                addError(sample);
                updateRules();
            } else {
                for (Sample sample : samples) {
                    addError(sample);
                }
                updateRules();
            }
            double mse = mse();
            errors.add(mse);

            if (debug && (i+1) % 500 == 0) {
                System.out.println("#" + (i+1) + ": mse= " + mse);
            }
        }
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<Double> getErrors() {
        return errors;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public IFunction getFunction() {
        return fx;
    }

    public double getError(double x, double y) {
        return getO(x, y) - fx.getF(x, y);
    }
}
