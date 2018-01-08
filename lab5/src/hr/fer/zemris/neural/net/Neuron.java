package hr.fer.zemris.neural.net;

import java.util.Random;

public class Neuron {
    private static Random random = new Random();

    private double w[];
    private double dW[];
    private double w0;
    private double dW0;
    private IActivationFunction actF;
    private double d;
    private double output;

    public Neuron(double[] w, double w0, IActivationFunction actF) {
        this.w = w;
        this.w0 = w0;
        this.actF = actF;
        clearGradient();
    }

    public Neuron(int size, IActivationFunction actF) {
        this.w = new double[size];
        this.w0 = random.nextDouble() * 2 - 1;
        for (int i = 0; i < size; ++i) {
            w[i] = random.nextDouble() * 2 - 1;
        }
        this.actF = actF;
        clearGradient();
    }

    public void clearGradient() {
        dW = new double[w.length];
        dW0 = 0;
    }
    public double[] getW() {
        return w;
    }

    public double getW(int i) {
        return w[i];
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setDelta(Layer next) {
        this.d = 0;
        for (int i = 0; i < next.size(); ++i) {
            this.d += dfx() * w[i] * next.getDelta(i);
        }
    }

    public double getOutput() {
        return output;
    }

    public void addGradient(double[] input) {
        for (int i = 0; i < w.length; ++i) {
            dW[i] += input[i] * d;
        }
        w0 += d;
    }

    public void update(double learningRate) {
        for (int i = 0; i < w.length; ++i) {
            w[i] += learningRate * dW[i];
        }
        w0 += learningRate * dW0;
        d = 0;
        clearGradient();
    }

    public void updateWeights(Layer next, double learningRate) {
        for (int i = 0; i < next.size(); ++i) {
            dW[i] += learningRate * getOutput() * next.getDelta(i);
        }
    }

    public void setWeights() {
        for (int i = 0; i < w.length; ++i) {
            w[i] += dW[i];
        }
    }

    public double predict(double[] input) {
        double ans = w0;
        for (int i = 0; i < w.length; ++i) {
            ans += w[i] * input[i];
        }
        output = actF.fx(ans);
        return output;
    }

    public IActivationFunction getF() {
        return actF;
    }

    public double fx(double x) {
        return actF.fx(x);
    }

    public double dfx(double x) {
        return actF.dfx(x);
    }

    public double fx() {
        return fx(getOutput());
    }

    public double dfx() {
        return dfx(getOutput());
    }

    public void addD(double dd) {
        d += dd;
    }
}
