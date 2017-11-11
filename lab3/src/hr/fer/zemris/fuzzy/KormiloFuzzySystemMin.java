package hr.fer.zemris.fuzzy;

public class KormiloFuzzySystemMin extends FuzzySystem {

    static {

    }

    public KormiloFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public int decide(int... inputs) {
        return 0;
    }
}
