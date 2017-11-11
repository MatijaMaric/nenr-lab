package hr.fer.zemris.fuzzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FuzzyBoat {

    public static void main(String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int L=0,D=0,LK=0,DK=0,V=0,S=0,akcel,kormilo;
        String line = null;

        IDefuzzifier def = new COADefuzzifier();

        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        while(true) {
            if ((line = input.readLine()) != null) {
                if (line.charAt(0) == 'K') break;
                Scanner s = new Scanner(line);
                L = s.nextInt();
                D = s.nextInt();
                LK = s.nextInt();
                DK = s.nextInt();
                V = s.nextInt();
                S = s.nextInt();
            }

            akcel = fsAkcel.decide(L, D, LK, DK, V, S);
            kormilo = fsKormilo.decide(L, D, LK, DK, V, S);
            System.out.println(akcel + " " + kormilo);
            System.out.flush();
        }

    }
}
