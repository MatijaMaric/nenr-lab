package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {
    public StandardFuzzySets() {

    }

    public static IIntUnaryFunction lFunction(int alpha, int beta) {
        return x -> {
            if (x < alpha) return 1;
            if (x >= beta) return 0;
            return (beta - x) / (double) (beta - alpha);
        };
    }

    public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
        return x -> {
            if (x < alpha) return 0;
            if (x >= beta) return 1;
            return (x - alpha) / (double) (beta - alpha);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
        return x -> {
            if (x < alpha) return 0;
            if (x > gamma) return 0;
            if (x < beta) return (x - alpha) / (double) (beta - alpha);
            return (gamma - x) / (double) (gamma - beta);
        };
    }

}
