package hr.fer.zemris.fuzzy.demo;


import hr.fer.zemris.fuzzy.Debug;
import hr.fer.zemris.fuzzy.Domain;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomena;

public class DemoDomains {

    public static void main(String[] args) {
        IDomena d1 = Domain.intRange(0, 5);
        Debug.print(d1, "Elementi domene d1: ");

        IDomena d2 = Domain.intRange(0, 3);
        Debug.print(d2, "Elementi domene d2: ");

        IDomena d3 = Domain.combine(d1, d2);
        Debug.print(d3, "Elementi domene d3: ");

        System.out.println(d3.elementForIndex(0));
        System.out.println(d3.elementForIndex(5));
        System.out.println(d3.elementForIndex(14));
        System.out.println(d3.indexOfElement(DomainElement.of(4, 1)));
    }
}
