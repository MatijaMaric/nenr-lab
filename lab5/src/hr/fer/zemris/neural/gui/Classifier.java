package hr.fer.zemris.neural.gui;

import hr.fer.zemris.neural.gui.CanvasPanel.CanvasObserver;
import hr.fer.zemris.neural.net.NeuralNetwork;
import hr.fer.zemris.neural.net.Sample;
import hr.fer.zemris.neural.support.Curve;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;

import static hr.fer.zemris.neural.support.NeuralUtil.*;

public class Classifier extends JFrame {

    private static final int MINI_BATCH_SIZE = 20;

    public enum BatchSize {
        STOCHASTIC,
        MINIBATCH,
        BATCH
    };

    private NeuralNetwork nn;

    private BatchSize batchSize;

    private JLabel label;

    public Classifier(BatchSize batchSize) {

        this.batchSize = batchSize;
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        setLayout(new BorderLayout());

        CanvasPanel canvas = new CanvasPanel();
        canvas.addObserver(classifyOnDraw);
        c.add(canvas, BorderLayout.CENTER);

        label = new JLabel();
        c.add(label, BorderLayout.SOUTH);

        initNeuralNet();
    }

    public Classifier() {
        this(BatchSize.BATCH);
    }

    private void initNeuralNet() {
        long startTime = System.nanoTime();
        nn = new NeuralNetwork(0.1, 20, 5, 10, 10);
        try {
            List<Sample> samples = samplesFromFile(Paths.get("../lab5/samples"), 20, 5);
            samples = mirrorSamples(samples);
            List<List<Sample>> batches;
            switch (batchSize) {
                case MINIBATCH: batches = groupBatch(samples, MINI_BATCH_SIZE); break;
                case STOCHASTIC: batches = groupBatch(samples, 1); break;
                case BATCH:
                default: batches = groupBatch(samples, samples.size()); break;
            }
            nn.fit(batches, 10000, 1E-5);
            nn.logError(Paths.get("../lab5/" + batchSize.toString() + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        double miliseconds = duration / 1000000;

        System.out.println("Training lasted " + miliseconds + " ms");
    }

    private String argmax(double[] vals, String[] labels) {
        int idx = 0;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < vals.length; ++i) {
            if (vals[i] > max) {
                max = vals[i];
                idx = i;
            }
        }
        return labels[idx];
    }

    private CanvasObserver classifyOnDraw = new CanvasObserver() {
        @Override
        void drawn(Curve curve) {
            double[] classify = nn.predict(curve.getFeats(10).normalize().flatten());
            String[] labels = {"alpha", "beta", "gamma", "delta", "epsilon"};
            for (double d : classify) {
                System.out.print(d + " ");
            }
            System.out.println();
            String labelText = argmax(classify, labels);
            System.out.println(labelText);

            label.setText(labelText);


        }
    };
}
