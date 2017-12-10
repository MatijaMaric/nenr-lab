package hr.fer.zemris.genetic;

import java.util.List;

public class Population {
    private int size;
    private Individual[] individuals;

    public Population(Individual[] individuals) {
        this.individuals = individuals;
    }

    public Population(int size) {
        this.individuals = new Individual[size];
    }

}
