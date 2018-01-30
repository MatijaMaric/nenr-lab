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

import static hr.fer.zemris.neural.support.NeuralUtil.groupBatch;
import static hr.fer.zemris.neural.support.NeuralUtil.samplesFromFile;

public class Classifier extends JFrame {

    private NeuralNetwork nn;

    public Classifier() {

        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        setLayout(new BorderLayout());

        CanvasPanel canvas = new CanvasPanel();
        canvas.addObserver(classifyOnDraw);
        c.add(canvas, BorderLayout.CENTER);

        initNeuralNet();
    }

    private void initNeuralNet() {
        nn = new NeuralNetwork(0.1, 0.7, 20, 5, 10, 10);
        try {
            List<Sample> samples = samplesFromFile(Paths.get("../samples"), 20, 5);
            List<List<Sample>> batches = groupBatch(samples, samples.size());
            nn.fit(batches, 10000, 1E-5);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            System.out.println(argmax(classify, labels));
        }
    };
}
