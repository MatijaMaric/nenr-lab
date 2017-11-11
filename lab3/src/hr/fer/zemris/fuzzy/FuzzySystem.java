package hr.fer.zemris.fuzzy;

public abstract class FuzzySystem {

    private IDefuzzifier defuzzifier;

    public FuzzySystem(IDefuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    public IDefuzzifier getDefuzzifier() {
        return defuzzifier;
    }

    public abstract int decide(int... inputs);
}
