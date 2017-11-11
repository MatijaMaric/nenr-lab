package hr.fer.zemris.fuzzy;

public class AkcelFuzzySystemMin extends FuzzySystem {

    static {

    }

    public AkcelFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public int decide(int... inputs) {

        return 0;
    }
}
