package hr.fer.zemris.neural.net;

public interface IActivationFunction {

    double fx(double x);
    double dfx(double x);
}
