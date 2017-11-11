package hr.fer.zemris.fuzzy;

public class AkcelFuzzySystemMin extends FuzzySystem {

    static {

    }

    public AkcelFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public int decide(int... inputs) {
        int L, D, LK, DK, V, S;
        L = inputs[0];
        D = inputs[1];
        LK = inputs[2];
        DK = inputs[3];
        V = inputs[4];
        S = inputs[5];


        return 0;
    }
}
