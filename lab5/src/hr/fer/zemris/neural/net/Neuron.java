package hr.fer.zemris.neural.net;


import java.util.Random;

import static hr.fer.zemris.neural.support.NeuralUtil.*;

public class Neuron {

    private static Random random = new Random();

    private double[] weights;
    private double[] correction;

    private double error;
    private double y;

    public Neuron(int size) {
        weights = randomize(size);
        correction = new double[size];
    }

    private double[] randomize(int size) {
        double[] array = new double[size];
        for (int i = 0; i < array.length; ++i) {
            array[i] = random.nextDouble();
        }
        return array;
    }

    public double predict(double[] input) {
        y = sigm(product(weights, input));
        return y;
    }

    public void applyCorrection() {
        for (int i = 0; i < weights.length; ++i) {
            weights[i] += correction[i];
        }
        correction = new double[correction.length];
    }

    public void addCorrection(Layer next, double learningRate) {
        for (int i = 0; i < next.getNeurons().size(); ++i) {
            correction[i] += learningRate * next.getNeurons().get(i).getError();
        }
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void correctError(Layer next) {
        error = 0;
        for (int i = 0; i < next.getNeurons().size(); ++i) {
            error += y * weights[i] * next.getNeurons().get(i).getError();
        }
    }

    public double getY() {
        return y;
    }
}
