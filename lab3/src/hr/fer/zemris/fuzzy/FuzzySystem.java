package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;

public abstract class FuzzySystem {

    private IDefuzzifier defuzzifier;
    private List<Rule> rules;

    private IFuzzySet decision = null;

    private IBinaryFunction or = Operations.zadehOr();

    public FuzzySystem(IDefuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
        this.rules = new ArrayList<>();
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
            decision = Operations.binaryOperation(decision, ruleD, or);
        }
        this.decision = decision;
        return getDefuzzifier().defuzzify(decision);
    }

    public IFuzzySet getDecision() {
        return decision;
    }
}
