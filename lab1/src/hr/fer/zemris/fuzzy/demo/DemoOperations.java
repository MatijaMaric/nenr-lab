package hr.fer.zemris.fuzzy.demo;

import hr.fer.zemris.fuzzy.*;

public class DemoOperations {

    public static void main(String[] args) {
        IDomena d = Domain.intRange(0, 11);

        IFuzzySet set1 = new MutableFuzzySet(d)
                .set(DomainElement.of(0), 1.0)
                .set(DomainElement.of(1), 0.8)
                .set(DomainElement.of(2), 0.6)
                .set(DomainElement.of(3), 0.4)
                .set(DomainElement.of(4), 0.2);
        Debug.print(set1, "Set1: ");

        IDomena d2 = Domain.intRange(-5, 6);
        IFuzzySet set2 = new CalculatedFuzzySet(
                d2,
                StandardFuzzySets.lambdaFunction(
                        d2.indexOfElement(DomainElement.of(-4)),
                        d2.indexOfElement(DomainElement.of( 0)),
                        d2.indexOfElement(DomainElement.of( 4))
                )
        );
        Debug.print(set2, "Set2: ");

        IFuzzySet notSet1 = Operations.unaryOperation(set1, Operations.zadehNot());
        Debug.print(notSet1, "notSet1: ");

        IFuzzySet union = Operations.binaryOperation(set1, notSet1, Operations.zadehOr());
        Debug.print(union, "Set1 union notSet1");

        IFuzzySet hinters = Operations.binaryOperation(set1, notSet1, Operations.hamacherTNorm(1.0));
        Debug.print(hinters, "Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");

    }
}
