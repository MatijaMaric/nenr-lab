package hr.fer.zemris.neurogenetic;

import hr.fer.zemris.neurogenetic.support.Dataset;
import hr.fer.zemris.neurogenetic.support.Sample;

public class NeuralNetwork {

    private double[] neurons;
    private int[] layers;

    public NeuralNetwork(int... layers) {
        this.layers = layers;
    }

    public int getParamsCount() {
        int ans = 0;
        ans += layers[0] * layers[1] * 2;
        for (int i = 2; i < layers.length; ++i) {
            ans += layers[i] * (layers[i-1] + 1);
        }
        return ans;
    }

    public double[] calcOutput(double[] params, double[] input) {
        this.neurons = new double[getParamsCount()];
        neurons[0] = input[0];
        neurons[1] = input[1];
        int layerFrom = 0;
        int layerTo = 2;
        int p = 0;
        for (int i = 1; i < layers.length; ++i) {
            for (int j = 0; j < layers[i]; ++j) {
                double net = 0;
                for (int k = layerFrom; k < layerTo; ++k) {
                    if (i == 1) {
                        net += Math.abs(neurons[k] - params[p++]) / Math.abs(params[p++]);
                    } else {
                        net += params[p++] * neurons[k];
                    }
                }
                if (i == 1) {
                    neurons[layerTo+j] = 1. / (1 + net);
                } else {
                    neurons[layerTo+j] = 1. / (1 + Math.exp(-net));
                }
            }
            layerFrom = layerTo;
            layerTo += layers[i];
        }

        double[] output = new double[layers[layers.length-1]];
        for (int i = 0; i < output.length; ++i) {
            output[i] = neurons[layerFrom+i];
        }
        return output;
    }

    public double calcError(Dataset dataset, double[] params) {
        double error = 0;
        for (Sample sample : dataset.getSamples()) {
            double[] predict = calcOutput(params, sample.getInputs());
            double[] outputs = sample.getOutputs();
            for (int i = 0; i < predict.length; ++i) {
                error += Math.pow(predict[i] - outputs[i], 2);
            }
        }
        return error / dataset.getSize();
    }
}
