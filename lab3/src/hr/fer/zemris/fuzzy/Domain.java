package hr.fer.zemris.fuzzy;

public abstract class Domain implements IDomena {

    @Override
    public DomainElement elementForIndex(int index) {
        int i = 0;
        for (DomainElement element : this) {
            if (index == i++) return element;
        }
        return null;
    }

    @Override
    public int indexOfElement(DomainElement what) {
        int i = 0;
        for (DomainElement element : this) {
            if (what.equals(element)) return i;
            i++;
        }
        return -1;
    }

    public static IDomena intRange(int from, int to) {
        return new SimpleDomain(from, to);
    }

    public static IDomena combine(IDomena first, IDomena second) {
        return new CompositeDomain(new IDomena[]{first, second});
    }

}
