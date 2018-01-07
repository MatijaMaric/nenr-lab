package hr.fer.zemris.neurofuzzy.support;

import java.util.Random;

import static hr.fer.zemris.neurofuzzy.support.NFUtil.sigm;

public class Rule {

    public double p;
    public double q;
    public double r;

    public double a;
    public double b;
    public double c;
    public double d;

    public double dA, dB, dC, dD, dP, dQ, dR;

    private static Random random = new Random();

    public Rule() {
        p = 2 * random.nextDouble() - 1;
        q = 2 * random.nextDouble() - 1;
        r = 2 * random.nextDouble() - 1;

        a = 2 * random.nextDouble() - 1;
        b = 2 * random.nextDouble() - 1;
        c = 2 * random.nextDouble() - 1;
        d = 2 * random.nextDouble() - 1;
        dP = dQ = dR = dA = dB = dC = dD = 0;
    }

    public double getZ(double x, double y){
        return p*x + q*y + r;
    }

    public double getT(double x, double y) {
        return sigm(x, a, b) * sigm(y, c, d);
    }

    public void addError(Sample s, double o, double sA, double sAZ) {
        double alpha = sigm(s.x, a, b);
        double beta = sigm(s.y, c, d);
        double T = alpha * beta;

        double z = getZ(s.x, s.y);

        double dEo = s.z - o;
        double dOT = (sA * z - sAZ) / Math.pow(sA, 2);

        dP += dEo * T * s.x / sA;
        dQ += dEo * T * s.y / sA;
        dR += dEo * T / sA;

        dA += dEo * dOT * T * b * (1 - alpha);
        dB += dEo * dOT * T * (a - s.x) * (1 - alpha);
        dC += dEo * dOT * T * d * (1 - beta);
        dD += dEo * dOT * T * (c - s.y) * (1 - beta);
    }

    public void update(double learningRate) {

        p += learningRate * dP;
        q += learningRate * dQ;
        r += learningRate * dR;

        a += learningRate * dA;
        b += learningRate * dB;
        c += learningRate * dC;
        d += learningRate * dD;

        dP = dQ = dR = dA = dB = dC = dD = 0;
    }

}
