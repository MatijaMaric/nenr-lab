package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;

public abstract class FuzzySystem {

    private IDefuzzifier defuzzifier;
    private List<Rule> rules;

    private IFuzzySet decision = null;

    private IBinaryFunction tNorm = Operations.zadehOr();
    private IBinaryFunction sNorm = Operations.zadehOr();
    private IBinaryFunction implication = Operations.zadehOr();

    public FuzzySystem(IDefuzzifier defuzzifier, IBinaryFunction tNorm, IBinaryFunction sNorm, IBinaryFunction implication) {
        this.defuzzifier = defuzzifier;
        this.rules = new ArrayList<>();

        this.tNorm = tNorm;
        this.sNorm = sNorm;
        this.implication = implication;
    }

    public IDefuzzifier getDefuzzifier() {
        return defuzzifier;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void addRule(IFuzzySet[] antecedent, IFuzzySet consequent) {
        getRules().add(new Rule(antecedent, consequent, tNorm, implication));
    }

    public int decide(int... inputs) {
        if (rules.size() == 0) return 0;
        IFuzzySet decision = rules.get(0).decide(inputs);

        for (int i = 1; i < rules.size(); ++i) {
            IFuzzySet ruleD = rules.get(i).decide(inputs);
            decision = Operations.binaryOperation(decision, ruleD, sNorm);
        }
        this.decision = decision;
        return getDefuzzifier().defuzzify(decision);
    }

    public IFuzzySet getDecision() {
        return decision;
    }

}
