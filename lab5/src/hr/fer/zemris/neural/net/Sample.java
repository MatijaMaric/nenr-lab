package hr.fer.zemris.neural.net;

public class Sample {
    private double[] inputs;
    private double[] outputs;

    public Sample(double[] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public double getOutput(int i) {
        return outputs[i];
    }

    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
    }

    public static Sample fromString(String str, int inputSize, int outputSize) {
        String[] split = str.split(" ");
        double[] inputs = new double[inputSize];
        double[] outputs = new double[outputSize];

        for (int i = 0; i < inputSize; ++i) {
            inputs[i] = Double.valueOf(split[i]);
        }
        for (int i = 0; i < outputSize; ++i) {
            outputs[i] = Double.valueOf(split[inputSize + i]);
        }

        return new Sample(inputs, outputs);
    }
}
