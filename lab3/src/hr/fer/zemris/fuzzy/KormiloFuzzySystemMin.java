package hr.fer.zemris.fuzzy;

public class KormiloFuzzySystemMin extends FuzzySystem {

    {
        IDomena angle = Domain.intRange(-90, 91);
        IDomena distance = Domain.intRange(0, 1301);
        IDomena velocity = Domain.intRange(0, 51);
        IDomena direction = Domain.intRange(0, 2);

        IFuzzySet idDistance = IdentityFuzzySet.get(distance);
        IFuzzySet idVelocity = IdentityFuzzySet.get(velocity);
        IFuzzySet idDirection = IdentityFuzzySet.get(direction);

        IFuzzySet blizuZida = new CalculatedFuzzySet(distance, StandardFuzzySets.lFunction(50, 75));

        IFuzzySet skreniLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(30,50,70));
        IFuzzySet skreniDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(-70, -50, -30));

        IFuzzySet naprijed = new MutableFuzzySet(direction).set(DomainElement.of(1), 1);
        IFuzzySet natrag = new MutableFuzzySet(direction).set(DomainElement.of(0), 1);

        // L, D, LK, RK, V, S
        getRules().add(new Rule(new IFuzzySet[] {blizuZida, idDistance, idDistance, idDistance, idVelocity, naprijed}, skreniDesno));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, blizuZida, idDistance, idDistance, idVelocity, naprijed}, skreniLijevo));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, blizuZida, idDistance, idVelocity, naprijed}, skreniDesno));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, blizuZida, idVelocity, naprijed}, skreniLijevo));
    }

    public KormiloFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

}
