package hr.fer.zemris.neural.gui;

import hr.fer.zemris.neural.gui.CanvasPanel.CanvasObserver;
import hr.fer.zemris.neural.net.NeuralNetwork;
import hr.fer.zemris.neural.net.Sample;
import hr.fer.zemris.neural.net.Sigmoid;
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
        nn = new NeuralNetwork(0.1, new Sigmoid(), 50, 8, 8, 5);
        try {
            List<Sample> samples = samplesFromFile(Paths.get("../samples"), 50, 5);
            List<List<Sample>> batches = groupBatch(samples, samples.size());
            nn.fit(batches, 10000, 1E-5);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private CanvasObserver classifyOnDraw = new CanvasObserver() {
        @Override
        void drawn(Curve curve) {
            double[] classify = nn.predict(curve.flatten());
            for (double d : classify) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    };
}
