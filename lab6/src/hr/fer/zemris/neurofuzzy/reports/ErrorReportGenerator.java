package hr.fer.zemris.neurofuzzy.reports;

import hr.fer.zemris.neurofuzzy.ANFIS;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ErrorReportGenerator {


    private static String ERRORS_DIR = "./lab6/error.txt";
    private static String SAMPLES_DIR = "./lab6/sample.txt";
    private static String PREDICT_DIR = "./lab6/predict.txt";

    private static int MAX_ITERS = 100000;
    private static int RULES_COUNT = 10;
    private static double ETA = 0.0005;

    public static void main(String[] args) throws IOException {
        ANFIS anfis = new ANFIS(ETA, RULES_COUNT, MAX_ITERS);
        anfis.fit(false, true);

        double from = -4;
        double to = 4;
        int resolution = 100;
        double step = (to - from) / resolution;

        Writer w = Files.newBufferedWriter(Paths.get(ERRORS_DIR), StandardCharsets.UTF_8);
        Writer wS = Files.newBufferedWriter(Paths.get(SAMPLES_DIR), StandardCharsets.UTF_8);
        Writer wP = Files.newBufferedWriter(Paths.get(PREDICT_DIR), StandardCharsets.UTF_8);

        for (int i = 0; i < resolution; ++i) {
            for (int j = 0; j < resolution; ++j) {
                double x = from + i * step;
                double y = from + j * step;
                double error = anfis.getError(x, y);
                double sample = anfis.getFunction().getF(x, y);
                double predict = anfis.predict(x, y);
                w.write(error + " ");
                wS.write(sample + " ");
                wP.write(predict + " ");
            }
            w.write("\n");
            wS.write("\n");
            wP.write("\n");
        }

        w.flush();
        w.close();
        wS.flush();
        wS.close();
        wP.flush();
        wP.close();

    }

}
