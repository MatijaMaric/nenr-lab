package hr.fer.zemris.neurofuzzy;

public class HWFunction implements IFunction {
    @Override
    public double getF(double x, double y) {
        return (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3) * Math.pow(Math.cos(x / 5), 2);
    }
}
