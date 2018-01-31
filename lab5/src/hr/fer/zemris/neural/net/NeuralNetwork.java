package hr.fer.zemris.neural.net;

import hr.fer.zemris.neural.support.NeuralUtil;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private double learningRate;

    private List<Neuron> inputLayer = new ArrayList<>();
    private List<List<Neuron>> hiddenLayers = new ArrayList<>();
    private List<Neuron> outputLayer = new ArrayList<>();

    private List<Double> errors = new ArrayList<>();

    public NeuralNetwork(double learningRate, int inputs, int outputs, int... hidden) {
        this.learningRate = learningRate;

        for (int i = 0; i < inputs; ++i) {
            inputLayer.add(new Neuron());
        }

        for (int i = 0; i < hidden.length; ++i) {
            List<Neuron> hiddenLayer = new ArrayList<>();
            for (int j = 0; j < hidden[i]; ++j) {
                Neuron neuron = new Neuron();
                hiddenLayer.add(neuron);
                if (i == 0) {
                    neuron.addConnections(inputLayer);
                } else {
                    neuron.addConnections(hiddenLayers.get(i-1));
                }
            }
            hiddenLayers.add(hiddenLayer);
        }

        for (int i = 0; i < outputs; ++i) {
            Neuron neuron = new Neuron();
            neuron.addConnections(hiddenLayers.get(hiddenLayers.size()-1));
            outputLayer.add(neuron);
        }

        Neuron.counter = 0;
        Connection.counter = 0;
    }

    private void setInputs(double[] inputs) {
        for (int i = 0; i < inputLayer.size(); ++i) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }

    private double[] getOutput() {
        double[] output = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); ++i) {
            output[i] = outputLayer.get(i).getOutput();
        }
        return output;
    }

    private void calcOutput() {
        for (List<Neuron> layer : hiddenLayers) {
            layer.forEach(Neuron::calcOutput);
        }
        outputLayer.forEach(Neuron::calcOutput);
    }

    private void backprop(double[] output) {
        int i = 0;
        for (Neuron neuron : outputLayer) {
            double o = neuron.getOutput();
            double y = output[i++];
            double d = (y - o) * neuron.actdFx(o);
            updateConnections(neuron, d);
        }
        double[] prevDs = new double[0];
        for (int j = hiddenLayers.size()-1; j >= 0; --j) {
            int n = 0;
            double[] Ds = new double[hiddenLayers.get(j).size()];
            for (Neuron neuron : hiddenLayers.get(j)) {
                double o = neuron.getOutput();
                double sum = 0;

                if (j == hiddenLayers.size()-1) {
                    int k = 0;
                    for (Neuron nextN : outputLayer) {
                        double wk = nextN.getConnection(neuron.id).getW();
                        double yk = output[k++];
                        double ok = nextN.getOutput();
                        sum += (yk - ok) * neuron.actdFx(ok) * wk;
                    }
                } else {
                    int k = 0;
                    for (Neuron nextN : hiddenLayers.get(j+1)) {
                        double wk = nextN.getConnection(neuron.id).getW();
                        sum += prevDs[k++] * wk;
                    }
                }
                double d = neuron.actdFx(o) * sum;
                Ds[n++] = d;
                updateConnections(neuron, d);
            }
            prevDs = Ds;
        }
    }

    private void updateConnections(Neuron neuron, double d) {
        for (Connection connection : neuron.getConnections()) {
            Neuron left = connection.getLeft();
            double dW = learningRate * d * left.getOutput();
            connection.addW(dW);
        }
    }

    private boolean isCorrect(double[] h, double[] y) {
        int idxH, idxY; idxH = idxY = -1;
        double maxH, maxY; maxH = maxY = Double.MIN_VALUE;
        for (int i = 0; i < h.length; ++i) {
            if (h[i] > maxH) {
                maxH = h[i];
                idxH = i;
            }
            if (y[i] > maxY) {
                maxY = y[i];
                idxY = i;
            }
        }
        return idxH == idxY;
    }

    public void fit(List<List<Sample>> batches, int maxIters, double errorTreshold) {
        List<Sample> samples = new ArrayList<>();
        batches.forEach(batch -> samples.addAll(batch));

        errors = new ArrayList<>();

        double error = Double.MAX_VALUE;
        int iter = 0;
        while (iter < maxIters && error > errorTreshold) {

            for (List<Sample> batch : batches) {
                for (Sample sample : batch) {
                    double[] inputs = sample.getInputs();
                    double[] outputs = sample.getOutputs();
                    setInputs(inputs);
                    calcOutput();
                    backprop(outputs);
                }
                updateWeights();
                iter++;
                error = getError(samples);
                errors.add(error);
                if ((iter+1) % 1000 == 0) {
                    System.out.println((iter+1) + "#: mse = " + error);
                }
                if (iter == maxIters) break;
            }

        }
    }

    private double getError(List<Sample> samples) {
        double error = 0;
        for (Sample sample : samples) {
            setInputs(sample.getInputs());
            calcOutput();
            double[] predict = getOutput();
            error += NeuralUtil.mse(predict, sample.getOutputs());
        }
        return error;
    }

    private void updateWeights() {
        for (List<Neuron> layer : hiddenLayers) {
            layer.forEach(Neuron::update);
        }
        outputLayer.forEach(Neuron::update);
    }

    public double[] predict(double[] inputs) {
        setInputs(inputs);
        calcOutput();
        return getOutput();
    }

    public void logError(Path path) throws IOException {
        Writer w = Files.newBufferedWriter(path, StandardCharsets.UTF_8);

        for (Double error : errors) {
            w.write(error + "\n");
        }
        w.flush();
        w.close();
    }
}
