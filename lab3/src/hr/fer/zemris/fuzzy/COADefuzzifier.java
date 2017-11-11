package hr.fer.zemris.fuzzy;

public class COADefuzzifier implements IDefuzzifier {

    @Override
    public int defuzzify(IFuzzySet fuzzySet) {
        float numerator = 0;
        float denominator = 0;

        for (DomainElement element : fuzzySet.getDomain()) {
            numerator += element.getComponentValue(0) * fuzzySet.getValueAt(element);
            denominator += fuzzySet.getValueAt(element);
        }

        return Math.round(numerator/denominator);
    }
}
