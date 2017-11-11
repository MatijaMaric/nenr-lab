package hr.fer.zemris.fuzzy;

public interface IFuzzySet {

    IDomena getDomain();

    double getValueAt(DomainElement element);
}
