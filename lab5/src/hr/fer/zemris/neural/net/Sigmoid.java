package hr.fer.zemris.neural.net;

import hr.fer.zemris.neural.support.NeuralUtil;

public class Sigmoid implements IActivationFunction {
    @Override
    public double fx(double x) {
        return NeuralUtil.sigm(x);
    }

    @Override
    public double dfx(double x) {
        return NeuralUtil.dSigm(x);
    }
}
