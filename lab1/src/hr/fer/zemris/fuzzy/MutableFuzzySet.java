package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {

    private IDomena domain;
    private double[] memberships;

    public MutableFuzzySet(IDomena domain) {
        this.domain = domain;
        memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomena getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return memberships[domain.indexOfElement(element)];
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        memberships[domain.indexOfElement(element)] = value;
        return this;
    }
}
