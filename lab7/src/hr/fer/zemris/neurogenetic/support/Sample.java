package hr.fer.zemris.neurogenetic.support;

public class Sample {
    public double x;
    public double y;
    public double o1;
    public double o2;
    public double o3;

    public Sample(double x, double y, double o1, double o2, double o3) {
        this.x = x;
        this.y = y;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
    }

    public static Sample fromString(String str) {
        String[] seg = str.split("\t");
        return new Sample(Double.parseDouble(seg[0]),
                          Double.parseDouble(seg[1]),
                          Double.parseDouble(seg[2]),
                          Double.parseDouble(seg[3]),
                          Double.parseDouble(seg[4]));
    }

    public Sample(double[] inputs, double[] outputs) {
        this(inputs[0], inputs[1], outputs[0], outputs[1], outputs[2]);
    }

    public double[] getOutputs() {
        double[] ans = new double[3];
        ans[0] = o1;
        ans[1] = o2;
        ans[2] = o3;
        return ans;
    }

    public double[] getInputs() {
        double[] ans = new double[2];
        ans[0] = x;
        ans[1] = y;
        return ans;
    }
}
