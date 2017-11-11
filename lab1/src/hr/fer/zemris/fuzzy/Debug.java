package hr.fer.zemris.fuzzy;

public class Debug {

    public static void print(IFuzzySet set, String label) {
        if (label != null) System.out.println(label);
        for (DomainElement element : set.getDomain()) {
            System.out.println("d(" + element + ")=" + set.getValueAt(element));
        }
        System.out.println();
    }

    public static void print(IDomena domain, String label) {
        if (label != null) System.out.println(label);
        for (DomainElement element : domain) {
            System.out.println("Element: " + element);
        }
        System.out.println("Kardinalitet: " + domain.getCardinality());
        System.out.println();
    }
}
