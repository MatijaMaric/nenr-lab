package hr.fer.zemris.fuzzy;

import java.util.Arrays;
import java.util.List;

public class Rule {

    private List<IFuzzySet> antecedent;
    private IFuzzySet consequent;

    private IBinaryFunction tNorm;
    private IBinaryFunction implication;

    private IFuzzySet decision;

    public Rule(List<IFuzzySet> antecedent, IFuzzySet consequent, IBinaryFunction tNorm, IBinaryFunction implication) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.tNorm = tNorm;
        this.implication = implication;
    }

    public Rule(IFuzzySet[] antecedent, IFuzzySet consequent, IBinaryFunction tNorm, IBinaryFunction implication) {
        this(Arrays.asList(antecedent), consequent, tNorm, implication);
    }

    public IFuzzySet decide(int... inputs) {

        IDomena domain = consequent.getDomain();
        MutableFuzzySet decision = new MutableFuzzySet(domain);

        for (DomainElement element : domain) {
            double val = antecedent.get(0).getValueAt(DomainElement.of(inputs[0]));

            for (int i = 1; i < antecedent.size(); ++i) {
                val = tNorm.valueAt(val, antecedent.get(i).getValueAt(DomainElement.of(inputs[i])));
            }

            decision.set(element, implication.valueAt(val, consequent.getValueAt(element)));

        }

        this.decision = decision;
        return decision;

    }

    public IFuzzySet getDecision() {
        return decision;
    }
}
