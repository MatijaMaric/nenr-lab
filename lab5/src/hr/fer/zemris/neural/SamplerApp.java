package hr.fer.zemris.neural;

import hr.fer.zemris.neural.gui.Sampler;

import javax.swing.*;

public class SamplerApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new Sampler();
            f.setVisible(true);
        });
    }
}
