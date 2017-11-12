package hr.fer.zemris.fuzzy;

public class KormiloFuzzySystem extends FuzzySystem {

    {
        IDomena angle = Domain.intRange(-90, 91);
        IDomena distance = Domain.intRange(0, 1301);
        IDomena velocity = Domain.intRange(0, 101);
        IDomena direction = Domain.intRange(0, 2);

        IFuzzySet idDistance = IdentityFuzzySet.get(distance);
        IFuzzySet idVelocity = IdentityFuzzySet.get(velocity);
        IFuzzySet idDirection = IdentityFuzzySet.get(direction);

        IFuzzySet blizuZida = new CalculatedFuzzySet(distance, StandardFuzzySets.lFunction(45, 65));

        IFuzzySet skreniLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.gammaFunction(110, 130));
        IFuzzySet skreniOstroLijevo = new CalculatedFuzzySet(angle, StandardFuzzySets.gammaFunction(150, 180));
        IFuzzySet skreniDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lFunction(50, 70));
        IFuzzySet skreniOstroDesno = new CalculatedFuzzySet(angle, StandardFuzzySets.lFunction(0, 30));

        IFuzzySet kriviSmjer = new MutableFuzzySet(direction).set(DomainElement.of(0), 1);

        // L, D, LK, RK, V, S

        addRule(new IFuzzySet[] {idDistance,    idDistance,      idDistance,     blizuZida,     idVelocity,     idDirection},   skreniOstroLijevo);
        addRule(new IFuzzySet[] {idDistance,    idDistance,     blizuZida,     idDistance,     idVelocity,     idDirection},   skreniOstroDesno);
        addRule(new IFuzzySet[] {idDistance,    idDistance,     idDistance,     idDistance,     idVelocity,     kriviSmjer},   skreniOstroLijevo);
    }

    public KormiloFuzzySystem(IDefuzzifier defuzzifier, IBinaryFunction tNorm, IBinaryFunction sNorm, IBinaryFunction implication) {
        super(defuzzifier, tNorm, sNorm, implication);
    }

}
