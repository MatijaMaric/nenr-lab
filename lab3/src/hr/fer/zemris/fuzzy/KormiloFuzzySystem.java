package hr.fer.zemris.fuzzy;

public class KormiloFuzzySystem extends FuzzySystem {

    {
        IDomena angle = Domain.intRange(-90, 91);
        IDomena distance = Domain.intRange(0, 1301);
        IDomena velocity = Domain.intRange(0, 51);
        IDomena direction = Domain.intRange(0, 2);

        IFuzzySet idDistance = IdentityFuzzySet.get(distance);
        IFuzzySet idVelocity = IdentityFuzzySet.get(velocity);
        IFuzzySet idDirection = IdentityFuzzySet.get(direction);

        IFuzzySet blizuZida = new CalculatedFuzzySet(distance, StandardFuzzySets.lambdaFunction(75, 100, 125));
        IFuzzySet jakoBlizuZida = new CalculatedFuzzySet(distance, StandardFuzzySets.lFunction(50, 100));

        IFuzzySet skreniLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(0,15,30));
        IFuzzySet skreniOstroLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(20,40,60));
        IFuzzySet skreniDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(-30, -15, 0));
        IFuzzySet skreniOstroDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lambdaFunction(-60, -40, -20));

        IFuzzySet naprijed = new MutableFuzzySet(direction).set(DomainElement.of(1), 1);
        IFuzzySet natrag = new MutableFuzzySet(direction).set(DomainElement.of(0), 1);

        // L, D, LK, RK, V, S

        addRule(new IFuzzySet[] {idDistance,    blizuZida,      idDistance,     idDistance,     idVelocity,     idDirection},   skreniLijevo);
        addRule(new IFuzzySet[] {idDistance,    jakoBlizuZida,  idDistance,     idDistance,     idVelocity,     idDirection},   skreniOstroLijevo);
        addRule(new IFuzzySet[] {blizuZida,     idDistance,     idDistance,     idDistance,     idVelocity,     idDirection},   skreniDesno);
        addRule(new IFuzzySet[] {jakoBlizuZida, idDistance,     idDistance,     idDistance,     idVelocity,     idDirection},   skreniOstroDesno);
    }

    public KormiloFuzzySystem(IDefuzzifier defuzzifier, IBinaryFunction tNorm, IBinaryFunction sNorm, IBinaryFunction implication) {
        super(defuzzifier, tNorm, sNorm, implication);
    }

}
