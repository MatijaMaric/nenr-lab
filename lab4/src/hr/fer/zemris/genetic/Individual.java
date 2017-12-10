package hr.fer.zemris.genetic;

public class Individual {
    private double[] genes;

    public Individual(double[] genes) {
        this.genes = genes;
    }

    public Individual(int size) {
        this.genes = new double[size];
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

}
