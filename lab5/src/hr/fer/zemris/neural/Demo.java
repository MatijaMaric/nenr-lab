package hr.fer.zemris.neural;

import hr.fer.zemris.neural.gui.Classifier;

import javax.swing.*;

public class Demo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Classifier classifier = new Classifier();
            classifier.setVisible(true);
        });
    }
}
