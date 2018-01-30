package hr.fer.zemris.neural.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Neuron {

    static int counter = 0;
    final int id;

    private IActivationFunction activationFunction;

    private List<Connection> connections = new ArrayList<>();
    private HashMap<Integer, Connection> lookup = new HashMap<>();

    private double output;

    public Neuron() {
        this(new Sigmoid());
    }

    public Neuron(IActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        id = counter++;
    }

    public void calcOutput() {
        double net = 0;
        for (Connection connection : connections) {
            Neuron left = connection.getLeft();
            double w = connection.getW();
            double o = left.getOutput();
            net += w * o;
        }
        output = actFx(net);
    }

    public void addConnections(List<Neuron> neurons) {
        for (Neuron neuron : neurons) {
            Connection connection = new Connection(neuron);
            connections.add(connection);
            lookup.put(neuron.id, connection);
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Connection getConnection(int neuronId) {
        return lookup.get(neuronId);
    }


    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double actFx(double x) {
        return activationFunction.fx(x);
    }

    public double actdFx(double x) {
        return activationFunction.dfx(x);
    }

    public void update() {
        connections.forEach(Connection::update);
    }
}
