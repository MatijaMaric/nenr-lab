package hr.fer.zemris.neural.net;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hr.fer.zemris.neural.support.NeuralUtil.*;

public class NeuralNetwork {

    private List<Layer> layers;
    private int maxIterations;
    private double maxError;
    private double learningRate;

    public NeuralNetwork(int maxIterations, double maxError, double learningRate, int inputShape, int outputShape, int... hiddenLayers) {
        this.layers = new ArrayList<>(hiddenLayers.length + 2);
        this.maxIterations = maxIterations;
        this.maxError = maxError;
        this.learningRate = learningRate;
        layers.add(new Layer(inputShape, hiddenLayers[0]));
        for (int i = 0; i < hiddenLayers.length-1; ++i) {
            layers.add(new Layer(hiddenLayers[i], hiddenLayers[i+1]));
        }
        layers.add(new Layer(hiddenLayers[hiddenLayers.length-1], outputShape));
    }

    public double[] predict(double[] input) {
        double[] ans = Arrays.copyOf(input, input.length);
        for (Layer layer : layers) {
            ans = layer.predict(ans);
        }
        return ans;
    }

    public void fit(List<List<Sample>> batches) {
        List<Sample> samples = batches.stream().reduce(new ArrayList<>(), (acc, batch) -> { acc.addAll(batch); return acc; });
        for (int i = 0; i < maxIterations; ++i) {
            double mse = 0;
            for (Sample sample : samples) {
                double[] h = predict(sample.getInputs());
                mse += meanSquareError(h, sample.getOutputs());
            }
            mse /= 2 * samples.size();

            if (mse < maxError) return;

            for (List<Sample> batch : batches) {
                for (Sample sample : batch) {
                    double[] h = predict(sample.getInputs());
                    correctError(sample.getOutputs());
                    addCorrection(learningRate);
                }
                correctWeights();
            }
        }
    }

    private void correctError(double[] t) {
        Layer last = layers.get(layers.size()-1);
        for (int i = 0; i < last.getNeurons().size(); ++i) {
            Neuron neuron = last.getNeurons().get(i);
            double o = neuron.getY();
            double err = o * (1-o) * (t[i] - o);
            neuron.setError(err);
        }
        for (int i = layers.size()-2; i >= 0; --i) {
            Layer layer = layers.get(i);
            layer.correctError(last);
            last = layer;
        }
    }

    private void addCorrection(double learningRate) {
        for (int i = layers.size()-2; i >= 0; --i) {
            layers.get(i).addCorrection(layers.get(i+1), learningRate);
        }
    }

    private void correctWeights() {
        for (Layer layer : layers) {
            layer.correctWeights();
        }
    }

    public static void main(String[] args) throws IOException {
        NeuralNetwork nn = new NeuralNetwork(10000, 1E-5, 0.2, 50, 5, 5, 5, 5);
        List<Sample> samples = samplesFromFile(Paths.get("../samples"), 50, 5);
        List<List<Sample>> batches = groupBatch(samples, samples.size());
        nn.fit(batches);

        System.out.println("heh");
    }

}
