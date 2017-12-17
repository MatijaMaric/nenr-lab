package hr.fer.zemris.neural.gui;

import hr.fer.zemris.neural.gui.CanvasPanel.CanvasObserver;
import hr.fer.zemris.neural.support.Curve;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sampler extends JFrame {

    private static Map<String, String> encoder = new TreeMap<>();
    static {
        encoder.put("alpha", "1 0 0 0 0");
        encoder.put("beta", "0 1 0 0 0");
        encoder.put("gamma", "0 0 1 0 0");
        encoder.put("delta", "0 0 0 1 0");
        encoder.put("epsilon", "0 0 0 0 1");
    }

    private String selectedLabel = "alpha";

    public Sampler() {
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        setLayout(new BorderLayout());

        CanvasPanel canvas = new CanvasPanel();
        canvas.addObserver(addOnDraw);
        c.add(canvas, BorderLayout.CENTER);

        initLabels(c);

    }

    private void initLabels(Container c) {
        JPanel labels = new JPanel();
        labels.setLayout(new FlowLayout());
        c.add(labels, BorderLayout.SOUTH);

        ButtonGroup groupLabels = new ButtonGroup();
        for (String key : encoder.keySet()) {
            JRadioButton rad = new JRadioButton(key);
            groupLabels.add(rad);
            labels.add(rad);

            rad.addItemListener(e -> {
                if (e.getStateChange() == 1) {
                    selectedLabel = key;
                }
            });
        }

        groupLabels.getElements().nextElement().setSelected(true);

    }

    private CanvasObserver addOnDraw = new CanvasObserver() {
        @Override
        void drawn(Curve curve) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("../samples"), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (curve.getLength() < 100) return;
                Curve features = curve.getFeats(25).normalize();
                System.out.println(features.getPoints().size());
                System.out.println(features.flatten().length);
                StringBuilder sb = new StringBuilder();
                for (double x : features.flatten()) {
                    sb.append(x).append(" ");
                }
                sb.append(encoder.get(selectedLabel));
                writer.write(sb.toString());
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                List<String> lines = Files.readAllLines(Paths.get("../samples"));
                Map<String, Integer> counter = new TreeMap<>();
                lines.forEach(s -> {
                    String key = s.substring(s.length()-9);
                    Integer count = counter.getOrDefault(key, 0);
                    counter.put(key, ++count);
                });
                System.out.println("alpha: " + counter.get("1 0 0 0 0"));
                System.out.println("beta: " + counter.get("0 1 0 0 0"));
                System.out.println("gamma: " + counter.get("0 0 1 0 0"));
                System.out.println("delta: " + counter.get("0 0 0 1 0"));
                System.out.println("epsilon: " + counter.get("0 0 0 0 1"));
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
