package hr.fer.zemris.fuzzy;

public class COADefuzzifier implements IDefuzzifier {

    @Override
    public int defuzzify(IFuzzySet fuzzySet) {
        int A = 0;
        int B = 0;

        for (DomainElement element : fuzzySet.getDomain()) {
            A += element.getComponentValue(0) * fuzzySet.getValueAt(element);
            B += fuzzySet.getValueAt(element);
        }

        return A/B;
    }
}
