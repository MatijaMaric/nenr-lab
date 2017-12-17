package hr.fer.zemris.neural.net;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> neurons;
    private double[] output;
    private double[] error;

    public Layer(int weightCount, int neuronCount) {
        this.neurons = new ArrayList<>(neuronCount);
        for (int i = 0; i < neuronCount; ++i) {
            neurons.add(new Neuron(weightCount));
        }

        output = new double[neuronCount];
        error = new double[neuronCount];
    }

    public double[] predict(double[] inputs) {
        double[] ans = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); ++i) {
            Neuron neuron = neurons.get(i);
            ans[i] = neuron.predict(inputs);
        }
        output = ans;
        return ans;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void correctError(Layer next) {
        for (Neuron neuron : neurons) {
            neuron.correctError(next);
        }
    }

    public void addCorrection(Layer next, double learningRate) {
        for (Neuron n : neurons) {
            n.addCorrection(next, learningRate);
        }
    }

    public void correctWeights() {
        for (Neuron n : neurons) {
            n.applyCorrection();
        }
    }

}
