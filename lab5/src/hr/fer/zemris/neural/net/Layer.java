package hr.fer.zemris.neural.net;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> neurons;
    private double outputs[];

    public Layer(int neuronCount, int weights, IActivationFunction activationFunction) {
        this.neurons = new ArrayList<>(neuronCount);
        for (int i = 0; i < neuronCount; ++i) {
            this.neurons.add(new Neuron(weights, activationFunction));
        }
    }

    public void updateDelta(Layer next) {
        for (int i = 0; i < neurons.size(); ++i) {
            double err = 0;
            for (Neuron neuron : next.neurons) {
                err += neuron.getW(i) * neuron.getD();
            }
            neurons.get(i).setD(err * neurons.get(i).dfx());
        }
    }

    public void addGradient(double[] input) {
        for (Neuron neuron : neurons) {
            neuron.addGradient(input);
        }
    }

    public void updateWeights(double learningRate) {
        for (Neuron neuron : neurons) {
            neuron.update(learningRate);
        }
    }

    public void setDeltas(Layer next) {
        for (Neuron neuron : neurons) {
            neuron.setDelta(next);
        }
    }

    public void updateWeights(Layer next, double learningRate) {
        for (Neuron neuron : neurons) {
            neuron.updateWeights(next, learningRate);
        }
    }

    public double[] predict(double[] input) {
        double[] ans = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); ++i) {
            ans[i] = neurons.get(i).predict(input);
        }
        this.outputs = ans;
        return ans;
    }

    public void setDeltas(double[] deltas) {
        for (int i = 0; i < neurons.size(); ++i) {
            neurons.get(i).setD(deltas[i]);
        }
    }

    public void setWeights() {
        for (Neuron neuron : neurons) {
            neuron.setWeights();
        }
    }

    public void addDeltas(double[] deltas) {
        for (int i = 0; i < neurons.size(); ++i) {
            neurons.get(i).addD(deltas[i]);
        }
    }

    public double[] getOutput() {
        return outputs;
    }

    public double getDelta(int j) {
        return neurons.get(j).getD();
    }

    public int size() {
        return neurons.size();
    }

}
