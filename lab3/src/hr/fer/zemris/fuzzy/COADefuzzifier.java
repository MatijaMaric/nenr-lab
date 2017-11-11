package hr.fer.zemris.fuzzy;

public class COADefuzzifier implements IDefuzzifier {

    @Override
    public int defuzzify(IFuzzySet set) {
        float numerator = 0;
        float denominator = 0;

        for (DomainElement element : set.getDomain()) {
            double value = set.getValueAt(element);
            numerator += element.getComponentValue(0) * value;
            denominator += value;
        }

        return Math.round(numerator/denominator);
    }
}
