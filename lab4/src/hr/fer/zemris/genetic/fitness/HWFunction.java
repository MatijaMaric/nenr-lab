package hr.fer.zemris.genetic.fitness;

import hr.fer.zemris.genetic.Individual;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class HWFunction implements IFitness {

    private int samplesCount;
    private double[] inputX;
    private double[] inputY;
    private double[] output;

    public HWFunction() {
    }

    public static HWFunction fromFile(Path path) throws IOException {
        HWFunction fx = new HWFunction();

        List<String> lines = Files.readAllLines(path);
        fx.samplesCount = lines.size();
        fx.inputX = new double[fx.samplesCount];
        fx.inputY = new double[fx.samplesCount];
        fx.output = new double[fx.samplesCount];

        for (int i = 0; i < fx.samplesCount; ++i) {
            String line = lines.get(i);
            Double[] s = (Double[]) Arrays.stream(line.split("\t")).map(Double::valueOf).toArray();
            fx.inputX[i] = s[0];
            fx.inputY[i] = s[1];
            fx.output[i] = s[2];
        }

        return fx;
    }

    @Override
    public double fitness(Individual individual) {
        return mse(individual);
    }

    private double mse(Individual individual) {
        double err = 0;
        for (int i = 0; i < samplesCount; ++i) {
            err += Math.pow(output[i] - f(inputX[i], inputY[i], individual.getGenes()), 2);
        }
        return err / samplesCount;
    }

    private double f(double x, double y, double[] b) {
        return Math.sin(b[0] + b[1] * x) + b[2] * Math.cos(x * (b[3] + y)) / (1 + Math.exp(x - b[4]));
    }
}
