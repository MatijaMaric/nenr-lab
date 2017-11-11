package hr.fer.zemris.fuzzy;

public class Relations {

    public Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        return relation.getDomain().getComponent(0).equals(relation.getDomain().getComponent(1));
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        IDomena u = relation.getDomain().getComponent(0);
        for (DomainElement first : u) {
            int x = first.getComponentValue(0);
            for (DomainElement second : u) {
                if (first.equals(second)) continue;
                int y = second.getComponentValue(0);
                double xy = relation.getValueAt(DomainElement.of(x, y));
                double yx = relation.getValueAt(DomainElement.of(y, x));

                if (Math.abs(xy - yx) > 1E-8) return false;
            }
        }
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        IDomena u = relation.getDomain().getComponent(0);
        for (DomainElement element : u) {
            int x = element.getComponentValue(0);
            double xx = relation.getValueAt(DomainElement.of(x, x));

            if (Math.abs(xx - 1.0) > 1E-8) return false;
        }
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        IDomena u = relation.getDomain().getComponent(0);
        for (DomainElement first : u) {
            int x = first.getComponentValue(0);
            for (DomainElement second : u) {
                int y = second.getComponentValue(0);
                double xy = relation.getValueAt(DomainElement.of(x, y));
                for (DomainElement third : u) {
                    int z = third.getComponentValue(0);
                    double xz = relation.getValueAt(DomainElement.of(x, z));
                    double yz = relation.getValueAt(DomainElement.of(y, z));

                    if (xz < Double.min(xy, yz)) return false;
                }
            }
        }
        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
        IDomena d = Domain.combine(r1.getDomain().getComponent(0), r2.getDomain().getComponent(1));
        MutableFuzzySet ans = new MutableFuzzySet(d);

        for (DomainElement element : d) {
            double value = 0.0;
            int x = element.getComponentValue(0);
            int z = element.getComponentValue(1);
            for (DomainElement yE : r1.getDomain().getComponent(1)) {
                int y = yE.getComponentValue(0);
                value = Double.max(
                        value,
                        Double.min(
                                r1.getValueAt(DomainElement.of(x, y)),
                                r2.getValueAt(DomainElement.of(y, z))
                        )
                );
            }

            ans.set(element, value);
        }

        return ans;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return isUtimesURelation(relation)
                && isReflexive(relation)
                && isSymmetric(relation)
                && isMaxMinTransitive(relation);
    }

}
