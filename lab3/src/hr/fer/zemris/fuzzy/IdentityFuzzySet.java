package hr.fer.zemris.fuzzy;

public class IdentityFuzzySet implements IFuzzySet {

    private IDomena domain;

    public IdentityFuzzySet(IDomena domain) {
        this.domain = domain;
    }

    @Override
    public IDomena getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return 1;
    }

    public static IFuzzySet get(IDomena domain) {
        return new IdentityFuzzySet(domain);
    }
}
