package hr.fer.zemris.neural.support;

import hr.fer.zemris.neural.net.Sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NeuralUtil {

    public static Random random = new Random();

    public static double sigm(double x) {
        return 1. / (1 + Math.exp(-x));
    }

    public static double dSigm(double x) {
        return x * (1-x);
    }

    public static double product(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; ++i) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static List<Sample> samplesFromFile(Path path, int inputSize, int outputSize) throws IOException {
        return Files.readAllLines(path).stream().map(s -> Sample.fromString(s, inputSize, outputSize)).collect(Collectors.toList());
    }

    public static List<List<Sample>> groupBatch(List<Sample> samples, int batchSize) {
        List<List<Sample>> batches = new ArrayList<>();
        List<Sample> batch = new ArrayList<>();
        List<Sample> shuffled = new ArrayList<>(samples);
        Collections.shuffle(shuffled);

        for (Sample sample : shuffled) {
            batch.add(sample);
            if (batch.size() == batchSize) {
                batches.add(batch);
                batch = new ArrayList<>();
            }
        }

        return batches;
    }

    public static double mse(double[] h, double[] y) {
        double mse = 0;
        for (int i = 0; i < h.length; ++i) mse += Math.pow(h[i] - y[i], 2);
        return mse / (2*h.length);
    }

    public static double[] delta(double[] a, double[] b) {
        double[] d = new double[a.length];
        for (int i = 0; i < a.length; ++i) {
            d[i] = a[i] - b[i];
        }
        return d;
    }

}
