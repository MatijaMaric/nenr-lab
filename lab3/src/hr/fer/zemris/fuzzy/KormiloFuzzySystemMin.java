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

        IFuzzySet skreniLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(0,20,40));
        IFuzzySet skreniDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(40, -20, 0));

        // L, D, LK, RK, V, S
        getRules().add(new Rule(new IFuzzySet[] {blizuZida, idDistance, idDistance, idDistance, idVelocity, idDirection}, skreniDesno));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, blizuZida, idDistance, idDistance, idVelocity, idDirection}, skreniLijevo));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, blizuZida, idDistance, idVelocity, idDirection}, skreniDesno));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, blizuZida, idVelocity, idDirection}, skreniLijevo));
    }

    public KormiloFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

}
