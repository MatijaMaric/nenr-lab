package hr.fer.zemris.neurofuzzy.reports;

import hr.fer.zemris.neurofuzzy.ANFIS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hr.fer.zemris.neurofuzzy.reports.helper.ReportUtils.errorsToFile;

public class GradientReportGenerator {

    private static String ERRORS_DIR = "./lab6/errors-gradient.txt";
    private static int MAX_ITERS = 100000;
    private static int RULES_COUNT = 10;
    private static double ETA = 0.0005;

    public static void main(String[] args) throws IOException {
        List<List<Double>> errorss = new ArrayList<>();
        ANFIS anfis = new ANFIS(ETA, RULES_COUNT, MAX_ITERS);
        anfis.fit(true, true);
        errorss.add(anfis.getErrors());
        anfis = new ANFIS(ETA, RULES_COUNT, MAX_ITERS);
        anfis.fit(false, true);
        errorss.add(anfis.getErrors());

        errorsToFile(errorss, ERRORS_DIR);
    }
}
