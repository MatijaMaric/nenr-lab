package hr.fer.zemris.neurofuzzy.support;

import hr.fer.zemris.neurofuzzy.IFunction;

public class Sample {

    public double x;
    public double y;
    public double z;

    public Sample(double x, double y, IFunction f) {
        this.x = x;
        this.y = y;
        this.z = f.getF(x, y);
    }

}
