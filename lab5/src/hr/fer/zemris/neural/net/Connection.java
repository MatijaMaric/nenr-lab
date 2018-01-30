package hr.fer.zemris.neural.net;

import hr.fer.zemris.neural.support.NeuralUtil;

public class Connection {

    private double w;
    private double dW;

    private Neuron left;
    static int counter = 0;
    final int id;


    public Connection(Neuron left) {
        this.left = left;
        id = counter++;
        w = NeuralUtil.random.nextDouble();
        dW = 0;
    }

    public double getW() {
        return w;
    }

    public Neuron getLeft() {
        return left;
    }

    public void addW(double w) {
        this.dW += w;
    }

    public void update() {
        w += dW;
        dW = 0;
    }
}
