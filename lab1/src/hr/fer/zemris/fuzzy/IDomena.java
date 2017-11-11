package hr.fer.zemris.fuzzy;

public interface IDomena extends Iterable<DomainElement> {

    int getCardinality();

    IDomena getComponent(int index);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);

}
