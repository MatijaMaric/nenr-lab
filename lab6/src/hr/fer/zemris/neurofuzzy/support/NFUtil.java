package hr.fer.zemris.neurofuzzy.support;

import java.util.Random;

public class NFUtil {

    private static Random random = new Random();

    public static double sigm(double x, double a, double b) {
        return 1. / (1 + Math.exp(b * (x - a)));
    }


}
