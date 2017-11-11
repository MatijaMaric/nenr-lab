package hr.fer.zemris.fuzzy;

import java.util.List;

public class Rule {

    private List<IFuzzySet> antecedent;
    private IFuzzySet consequent;

    private IBinaryFunction and = Operations.zadehAnd();

    public Rule(List<IFuzzySet> antecedent, IFuzzySet consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public IFuzzySet decide(int... inputs) {

        IDomena domain = consequent.getDomain();
        MutableFuzzySet decision = new MutableFuzzySet(domain);

        for (DomainElement element : domain) {
            double val = antecedent.get(0).getValueAt(DomainElement.of(inputs[0]));

            for (int i = 1; i < antecedent.size(); ++i) {
                val = and.valueAt(val, antecedent.get(i).getValueAt(DomainElement.of(inputs[i])));
            }

            decision.set(element, and.valueAt(val, consequent.getValueAt(element)));

        }

        return decision;

    }
}
