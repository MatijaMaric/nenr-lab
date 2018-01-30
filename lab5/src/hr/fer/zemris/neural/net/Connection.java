package hr.fer.zemris.neural.net;

import hr.fer.zemris.neural.support.NeuralUtil;

public class Connection {

    private double w;
    private double bestW;
    private double dW0;
    private double dW;

    private Neuron left;
    static int counter = 0;
    final int id;


    public Connection(Neuron left) {
        this.left = left;
        id = counter++;
        w = NeuralUtil.random.nextDouble();
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setBestW(double bestW) {
        this.bestW = bestW;
    }

    public void updateBest() {
        w = bestW;
    }

    public void setdW(double dW) {
        dW0 = this.dW;
        this.dW = dW;
    }

    public double getdW0() {
        return dW0;
    }

    public Neuron getLeft() {
        return left;
    }

    public void adddW(double dW) {
        this.dW += dW;
    }
}
