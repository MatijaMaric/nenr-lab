package hr.fer.zemris.fuzzy;


import java.math.BigDecimal;

public class Operations {
    public Operations() {
    }

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet newSet = new MutableFuzzySet(set.getDomain());
        for (DomainElement element : set.getDomain()) {
            newSet.set(element, function.valueAt(set.getValueAt(element)));
        }
        return newSet;
    }

    public static IFuzzySet binaryOperation(IFuzzySet first, IFuzzySet second, IBinaryFunction function) {
        MutableFuzzySet newSet = new MutableFuzzySet(first.getDomain());
        for (DomainElement element : first.getDomain()) {
            newSet.set(element, function.valueAt(first.getValueAt(element), second.getValueAt(element)));
        }
        return newSet;
    }

    public static IUnaryFunction zadehNot() {
        return x -> BigDecimal.ONE.subtract(BigDecimal.valueOf(x)).doubleValue();
    }

    public static IBinaryFunction zadehAnd() {
        return Double::min;
    }

    public static IBinaryFunction zadehOr() {
        return Double::max;
    }

    public static IBinaryFunction hamacherTNorm(double v) {
        return (x, y) -> {
            BigDecimal xD = BigDecimal.valueOf(x);
            BigDecimal yD = BigDecimal.valueOf(y);
            BigDecimal vD = BigDecimal.valueOf(v);

            return xD.multiply(yD)
                    .divide(
                            vD.subtract(
                                    BigDecimal.ONE.subtract(vD)
                                            .multiply(xD.add(yD).subtract(xD.multiply(yD)))
                            )
                    ).doubleValue();
        };
    }

    public static IBinaryFunction hamacherSNorm(double v) {
        return (x, y) -> {
            BigDecimal xD = BigDecimal.valueOf(x);
            BigDecimal yD = BigDecimal.valueOf(y);
            BigDecimal vD = BigDecimal.valueOf(v);

            return xD.add(yD)
                    .subtract(
                            BigDecimal.valueOf(2.0).subtract(vD)
                                    .multiply(xD.multiply(yD))
                    ).divide(
                            BigDecimal.ONE.subtract(
                                    BigDecimal.ONE.subtract(vD)
                                            .multiply(xD.multiply(yD))
                            )
                    ).doubleValue();
        };
    }
}
