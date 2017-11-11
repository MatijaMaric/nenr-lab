package hr.fer.zemris.fuzzy.demo;

import hr.fer.zemris.fuzzy.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Demo1 {

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int L=0,D=0,LK=0,DK=0,V=0,S=0;

        IDefuzzifier def = new COADefuzzifier();

        FuzzySystem fsKormilo = new KormiloFuzzySystem(def);

        Scanner s = new Scanner(input.readLine());
        L = s.nextInt();
        D = s.nextInt();
        LK = s.nextInt();
        DK = s.nextInt();
        V = s.nextInt();
        S = s.nextInt();

        int akcel = fsKormilo.decide(L, D, LK, DK, V, S);
        System.out.println(akcel);
        Debug.print(fsKormilo.getDecision(), "decision:");

    }
}
