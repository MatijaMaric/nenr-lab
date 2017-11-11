package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleDomain extends Domain {
    private int first;
    private int last;

    public SimpleDomain(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public int getCardinality() {
        return last - first;
    }

    @Override
    public IDomena getComponent(int index) {
        return this;
    }

    @Override
    public int getNumberOfComponents() {
        return 1;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<DomainElement>() {
            int next = first;
            @Override
            public boolean hasNext() {
                return next < last;
            }

            @Override
            public DomainElement next() {
                if (next >= last) throw new NoSuchElementException();
                return DomainElement.of(next++);
            }
        };
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDomain that = (SimpleDomain) o;

        if (first != that.first) return false;
        return last == that.last;
    }

}
