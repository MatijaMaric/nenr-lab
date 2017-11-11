package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {

    private IDomena domain;
    private IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomena domain, IIntUnaryFunction function) {
        this.domain = domain;
        this.function = function;
    }

    @Override
    public IDomena getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return this.function.valueAt(domain.indexOfElement(element));
    }
}
