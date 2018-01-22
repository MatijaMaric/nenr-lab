package hr.fer.zemris.neural.net;

public class Sigmoid implements IActivationFunction {
    @Override
    public double fx(double x) {
        return 1. / (1 + Math.exp(x));
    }

    @Override
    public double dfx(double x) {
        return x * (1-x);
    }
}
