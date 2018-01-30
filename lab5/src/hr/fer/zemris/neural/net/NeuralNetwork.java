package hr.fer.zemris.neural.net;

import hr.fer.zemris.neural.support.NeuralUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private double learningRate;
    private double momentum;

    private List<Neuron> inputLayer = new ArrayList<>();
    private List<List<Neuron>> hiddenLayers = new ArrayList<>();
    private List<Neuron> outputLayer = new ArrayList<>();

    public NeuralNetwork(double learningRate, double momentum, int inputs, int outputs, int... hidden) {
        this.learningRate = learningRate;
        this.momentum = momentum;

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
            double w = connection.getW() + dW;
            connection.setdW(dW);
            connection.setW(w + momentum * connection.getdW0());
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

        double error = Double.MAX_VALUE;
        int iter = 0;
        while (iter < maxIters && error > errorTreshold) {
            error = 0;
            int correct = 0;
            for (Sample sample : samples) {
                setInputs(sample.getInputs());
                calcOutput();
                double[] predict = getOutput();
                error += NeuralUtil.mse(predict, sample.getOutputs());
                correct += isCorrect(predict, sample.getOutputs()) ? 1 : 0;
            }

            for (List<Sample> batch : batches) {
                for (Sample sample : batch) {
                    double[] inputs = sample.getInputs();
                    double[] outputs = sample.getOutputs();
                    setInputs(inputs);
                    calcOutput();
                    backprop(outputs);
                }
                iter++;
            }
            if ((iter+1) % 1000 == 0) {
                System.out.println((iter+1) + "#: mse = " + error + " guessed: " + (correct / samples.size()) * 100 + "%" );
            }
        }
    }

    public double[] predict(double[] inputs) {
        setInputs(inputs);
        calcOutput();
        return getOutput();
    }
}
