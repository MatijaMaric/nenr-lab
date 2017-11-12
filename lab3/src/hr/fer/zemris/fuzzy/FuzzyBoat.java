package hr.fer.zemris.fuzzy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FuzzyBoat {

    private static IBinaryFunction tNorm = Operations.product();
    private static IBinaryFunction sNorm = Operations.zadehOr();
    private static IBinaryFunction implication = Operations.product();

    public static void main(String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int L=0,D=0,LK=0,DK=0,V=0,S=0,akcel,kormilo;
        String line = null;

        IDefuzzifier def = new COADefuzzifier();

        FuzzySystem fsAkcel = new AkcelFuzzySystem(def, tNorm, sNorm, implication);
        FuzzySystem fsKormilo = new KormiloFuzzySystem(def, tNorm, sNorm, implication);

        BufferedWriter log = Files.newBufferedWriter(Paths.get("D:\\log.txt"));

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

            log.write("in: " + line);
            log.newLine();
            log.flush();

            akcel = fsAkcel.decide(L, D, LK, DK, V, S);
            kormilo = fsKormilo.decide(L, D, LK, DK, V, S);
            System.out.println(akcel + " " + kormilo);
            log.write("out: " + akcel + " " + kormilo);
            log.newLine();
            log.flush();
            System.out.flush();
        }

    }
}
