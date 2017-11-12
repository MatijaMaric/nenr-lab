package hr.fer.zemris.fuzzy;

import java.util.ArrayList;

public class AkcelFuzzySystem extends FuzzySystem {

    {
        IDomena acceleration = Domain.intRange(-50, 51);
        IDomena distance = Domain.intRange(0, 1301);
        IDomena velocity = Domain.intRange(0, 101);
        IDomena direction = Domain.intRange(0, 2);

        IFuzzySet idDistance = IdentityFuzzySet.get(distance);
        IFuzzySet idDirection = IdentityFuzzySet.get(direction);

        IFuzzySet ubrzaj = new CalculatedFuzzySet(velocity, StandardFuzzySets.lambdaFunction(52, 55, 58));
        IFuzzySet uspori = new CalculatedFuzzySet(velocity, StandardFuzzySets.lFunction(40, 50));

        IFuzzySet sporo = new CalculatedFuzzySet(velocity, StandardFuzzySets.lFunction(20, 45));
        IFuzzySet prebrzo = new CalculatedFuzzySet(velocity, StandardFuzzySets.gammaFunction(55, 70));

        // L, D, LK, RK, V, S

        addRule(new IFuzzySet[] {idDistance,    idDistance,     idDistance,     idDistance,     prebrzo,     idDirection},    uspori);
        addRule(new IFuzzySet[] {idDistance,    idDistance,     idDistance,     idDistance,     sporo,     idDirection},    ubrzaj);

    }

    public AkcelFuzzySystem(IDefuzzifier defuzzifier, IBinaryFunction tNorm, IBinaryFunction sNorm, IBinaryFunction implication) {
        super(defuzzifier, tNorm, sNorm, implication);
    }

}
