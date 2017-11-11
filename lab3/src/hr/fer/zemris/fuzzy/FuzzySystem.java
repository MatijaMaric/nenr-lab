package hr.fer.zemris.fuzzy;

import java.util.List;

public abstract class FuzzySystem {

    private IDefuzzifier defuzzifier;
    private List<Rule> rules;

    public FuzzySystem(IDefuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    public IDefuzzifier getDefuzzifier() {
        return defuzzifier;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public int decide(int... inputs) {
        IFuzzySet decision = rules.get(0).decide(inputs);

        for (int i = 1; i < rules.size(); ++i) {
            IFuzzySet ruleD = rules.get(i).decide(inputs);
            Operations.binaryOperation(decision, ruleD, Operations.zadehOr());
        }

        return getDefuzzifier().defuzzify(decision);
    }
}
