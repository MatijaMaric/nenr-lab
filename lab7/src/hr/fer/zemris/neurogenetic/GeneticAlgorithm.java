package hr.fer.zemris.neurogenetic;

import hr.fer.zemris.neurogenetic.genetic.crossover.DiscreteRecombination;
import hr.fer.zemris.neurogenetic.genetic.crossover.ICrossover;
import hr.fer.zemris.neurogenetic.genetic.crossover.SimpleArithmeticRecombination;
import hr.fer.zemris.neurogenetic.genetic.crossover.WholeArithmeticRecombination;
import hr.fer.zemris.neurogenetic.genetic.mutation.AddGaussMutation;
import hr.fer.zemris.neurogenetic.genetic.mutation.IMutation;
import hr.fer.zemris.neurogenetic.genetic.mutation.ReplaceGaussMutation;
import hr.fer.zemris.neurogenetic.support.Dataset;
import hr.fer.zemris.neurogenetic.support.Sample;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private double SIGMA1 = 0.2;
    private double SIGMA2 = 0.2;

    private int MAX_ITER = 100000;
    private int POPULATION_SIZE = 25;

    private double P_MUTATOR = 0.6;
    private double P_M = 0.01;

    private NeuralNetwork nn;
    private Dataset dataset;

    private Random random;

    private List<ICrossover> crossovers;
    private IMutation mutator1;
    private IMutation mutator2;

    private double[][] population;
    private double[] errors;

    private int bestId;
    private double bestError = Double.MAX_VALUE;

    public GeneticAlgorithm(NeuralNetwork nn, Dataset dataset) {
        this.nn = nn;
        this.dataset = dataset;
        this.random = new Random();

        crossovers = new ArrayList<>();
        crossovers.add(new DiscreteRecombination(random));
        crossovers.add(new SimpleArithmeticRecombination(random));
        crossovers.add(new WholeArithmeticRecombination());

        mutator1 = new AddGaussMutation(random, SIGMA1, P_M);
        mutator2 = new ReplaceGaussMutation(random, SIGMA2, P_M);

        populate();
        calcErrors();
    }

    private void populate() {
        population = new double[POPULATION_SIZE][nn.getParamsCount()];
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            for (int j = 0; j < population[0].length; ++j) {
                population[i][j] = random.nextGaussian() * SIGMA2;
            }
        }
    }

    private void calcErrors() {
        errors = new double[POPULATION_SIZE];
        double minError = Double.MAX_VALUE;
        bestId = 0;
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            errors[i] = nn.calcError(dataset, population[i]);
            if (errors[i] < minError) {
                minError = errors[i];
                bestId = i;
            }
        }
    }

    public void evolve() {
        for (int iter = 0; iter < MAX_ITER * POPULATION_SIZE && bestError > 1E-7; ++iter) {
            int parent1, parent2, parent3;
            parent1 = random.nextInt(POPULATION_SIZE);
            while ((parent2 = random.nextInt(POPULATION_SIZE)) == parent1);
            while ((parent3 = random.nextInt(POPULATION_SIZE)) == parent1 || parent3 == parent2);

            if (errors[parent3] < errors[parent2]) {
                int tmp = parent2;
                parent2 = parent3;
                parent3 = tmp;
            }

            if (errors[parent3] < errors[parent1]) {
                int tmp = parent1;
                parent1 = parent3;
                parent3 = tmp;
            }

            int cross = random.nextInt(crossovers.size());
            population[parent3] = crossovers.get(cross).crossover(population[parent1], population[parent2]);

            if (random.nextDouble() < P_MUTATOR) {
                population[parent3] = mutator1.mutate(population[parent3]);
            } else {
                population[parent3] = mutator2.mutate(population[parent3]);
            }

            errors[parent3] = nn.calcError(dataset, population[parent3]);
            if (errors[parent3] < bestError) {
                bestError = errors[parent3];
                bestId = parent3;
            }

            if ((iter+1) % 1000 == 0) {
                System.out.println((iter+1) + "#: mse= " + bestError);
            }
        }
    }

    public double[] getBest(){
        return population[bestId];
    }

    public double[] predict(double[] inputs) {
        return nn.calcOutput(getBest(), inputs);
    }

    public double[] argmaxPredict(double[] inputs) {
        double[] output = predict(inputs);
        int idx = 0;
        for (int i = 0; i < output.length; ++i) {
            if (output[i] > output[idx]) {
                idx = i;
            }
        }
        double[] ans = new double[output.length];
        ans[idx] = 1;
        return ans;
    }

    public static void main(String[] args) throws IOException {


        NeuralNetwork nn = new NeuralNetwork(2, 8, 4, 3);
        Dataset dataset = Dataset.fromFile(Paths.get("D:\\FER\\NENR\\lab7\\zad7-dataset.txt"));
        GeneticAlgorithm ga = new GeneticAlgorithm(nn, dataset);

        ga.evolve();

        int correct = 0;
        for (Sample sample : dataset.getSamples()) {
            double[] input = sample.getInputs();
            double[] output = sample.getOutputs();
            double[] predict = ga.argmaxPredict(input);
            for (int i = 0; i < output.length; ++i) {
                if (output[i] == 1 && predict[i] == 1) {
                    correct++;
                    break;
                }
            }
        }

        double success = 100. * correct / dataset.getSize();
        System.out.println(success + "% samples classified correctly");
    }
}
