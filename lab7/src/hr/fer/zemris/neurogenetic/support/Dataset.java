package hr.fer.zemris.neurogenetic.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private List<Sample> samples;

    public Dataset() {
        samples = new ArrayList<>();
    }

    public static Dataset fromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Dataset dataset = new Dataset();
        for (String line : lines) {
            dataset.addSample(Sample.fromString(line));
        }
        return dataset;
    }

    public void addSample(Sample sample) {
        samples.add(sample);
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public Sample getSample(int i) {
        return samples.get(i);
    }

    public int getSize() {
        return samples.size();
    }
}
