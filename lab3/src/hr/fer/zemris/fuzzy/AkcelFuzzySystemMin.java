package hr.fer.zemris.fuzzy;

import java.util.ArrayList;

public class AkcelFuzzySystemMin extends FuzzySystem {

    {
        IDomena acceleration = Domain.intRange(-50, 50);
        IDomena distance = Domain.intRange(0, 1301);
        IDomena velocity = Domain.intRange(0, 51);
        IDomena direction = Domain.intRange(0, 2);

        IFuzzySet idDistance = IdentityFuzzySet.get(distance);
        IFuzzySet idVelocity = IdentityFuzzySet.get(velocity);
        IFuzzySet idDirection = IdentityFuzzySet.get(direction);

        IFuzzySet ubrzaj = new MutableFuzzySet(acceleration).set(DomainElement.of(5), 1);
        IFuzzySet uspori = new MutableFuzzySet(acceleration).set(DomainElement.of(-5), 1);
        IFuzzySet nulaAcc = new MutableFuzzySet(acceleration).set(DomainElement.of(0), 1);

        IFuzzySet blizuZida = new CalculatedFuzzySet(distance, StandardFuzzySets.lFunction(50, 75));

        IFuzzySet sporo = new CalculatedFuzzySet(velocity, StandardFuzzySets.lFunction(10, 20));
        IFuzzySet brzo = new CalculatedFuzzySet(velocity, StandardFuzzySets.gammaFunction(20,30));
        IFuzzySet prebrzo = new CalculatedFuzzySet(velocity, StandardFuzzySets.gammaFunction(30, 50));

        IFuzzySet naprijed = new MutableFuzzySet(direction).set(DomainElement.of(1), 1);
        IFuzzySet natrag = new MutableFuzzySet(direction).set(DomainElement.of(0), 1);

        // L, D, LK, RK, V, S
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, blizuZida, idDistance, brzo, naprijed}, nulaAcc));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, blizuZida, idDistance, brzo, natrag}, nulaAcc));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, blizuZida, brzo, naprijed}, nulaAcc));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, blizuZida, brzo, natrag}, nulaAcc));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, idDistance, sporo, idDirection}, ubrzaj));
        getRules().add(new Rule(new IFuzzySet[] {idDistance, idDistance, idDistance, idDistance, prebrzo, idDirection}, uspori));


    }

    public AkcelFuzzySystemMin(IDefuzzifier defuzzifier) {
        super(defuzzifier);
    }

}
