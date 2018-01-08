package hr.fer.zemris.neural.net;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private int inputSize;
    private List<Layer> layers;
    private double learningRate;
    private IActivationFunction activationFunction;

    public NeuralNetwork(double learningRate, IActivationFunction activationFunction, int... layerSizes) {
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.layers = new ArrayList<>(layerSizes.length-1);
        this.inputSize = layerSizes[0];
        for (int i = 1; i < layerSizes.length; ++i) {
            layers.add(new Layer(layerSizes[i], layerSizes[i-1], activationFunction));
        }
    }

    public double[] predict(double[] input) {
        double[] ans = input;
        for (Layer layer : layers) {
            ans = layer.predict(ans);
        }
        return ans;
    }

    public void fit(List<List<Sample>> batches, int maxIter, double minErr) {
        List<Sample> samples = new ArrayList<>();
        for (List<Sample> batch : batches) {
            samples.addAll(batch);
        }
        double mse = mse(samples);

        for (int iter = 0; iter < maxIter && mse > minErr; ++iter) {
            if ((iter + 1) % 500 == 0) {
                System.out.println("#" + (iter + 1) + ": mse= " + mse);
            }

            for (List<Sample> batch : batches) {
                for (Sample sample : batch) {
                    double[] input = sample.getInputs();
                    double[] target = sample.getOutputs();
                    double[] output = predict(input);

                    double[] delta = new double[output.length];
                    for (int i = 0; i < delta.length; ++i) {
                        delta[i] = activationFunction.dfx(output[i]) * (target[i] - output[i]);
                    }
                    layers.get(layers.size()-1).setDeltas(delta);

                    for (int i = layers.size()-2; i >= 0; --i) {
                        layers.get(i).updateWeights(layers.get(i+1), learningRate);
                        layers.get(i).updateDelta(layers.get(i+1));
                    }
                }

                for (Layer layer : layers) {
                    layer.setWeights();
                }
            }

            mse = mse(samples);
        }
    }

    private double mse(List<Sample> samples) {
        double err = 0;
        for (Sample sample : samples) {
            double[] t = predict(sample.getInputs());
            for (int i = 0; i < sample.getOutputs().length; ++i) {
                err += Math.pow(sample.getOutputs()[i] - t[i], 2);
            }
        }
        return err / (2 * samples.size());
    }
}
