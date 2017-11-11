package hr.fer.zemris.fuzzy;


import java.util.Iterator;

public class CompositeDomain extends Domain {

    private IDomena[] components;

    public CompositeDomain(IDomena[] components) {
        this.components = components;
    }

    @Override
    public int getCardinality() {
        if (components.length == 0) return 0;
        int i = 1;
        for (IDomena component : components) {
            i *= component.getCardinality();
        }
        return i;
    }

    @Override
    public IDomena getComponent(int index) {
        return components[index];
    }

    @Override
    public int getNumberOfComponents() {
        return components.length;
    }

    @Override
    public Iterator<DomainElement> iterator() {

        int componentCount = getNumberOfComponents();
        int[] indexes = new int[componentCount];
        int[] counts = new int[componentCount];
        int[] vals = new int[componentCount];
        Iterator<DomainElement>[] iterators = new Iterator[componentCount];

        for (int i = 0; i < componentCount; ++i) {
            counts[i] = components[i].getCardinality();
            iterators[i] = components[i].iterator();
            vals[i] = iterators[i].next().getComponentValue(0);
        }

        return new Iterator<>() {
            boolean isLast = false;
            boolean isDone = false;

            @Override
            public boolean hasNext() {
                boolean has = false;
                int last = 0;
                for (int i = 0; i < componentCount; ++i) {
                    if (indexes[i] == counts[i] - 1) last++;
                    if (indexes[i] < counts[i] - 1) has = true;
                }
                isLast = (last == componentCount);
                return (has || isLast) && !isDone;
            }

            @Override
            public DomainElement next() {
                DomainElement ans = DomainElement.of(vals);
                if (isLast) {
                    isDone = true;
                    return ans;
                }
                indexes[componentCount-1]++;
                if (indexes[componentCount-1] < counts[componentCount-1]) vals[componentCount-1] = iterators[componentCount-1].next().getComponentValue(0);
                for (int i = componentCount-1; i > 0; --i) {
                    if (indexes[i] == counts[i]) {
                        indexes[i] = 0;
                        indexes[i-1]++;
                        vals[i-1] = iterators[i-1].next().getComponentValue(0);
                        iterators[i] = components[i].iterator();
                        vals[i] = iterators[i].next().getComponentValue(0);
                    }
                }
                return ans;
            }
        };
    }

}
