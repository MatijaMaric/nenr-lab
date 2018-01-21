package hr.fer.zemris.neurofuzzy.reports;

import hr.fer.zemris.neurofuzzy.ANFIS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hr.fer.zemris.neurofuzzy.reports.helper.ReportUtils.errorsToFile;

public class EtaReportGenerator {

    private static String ERRORS_STOCHASTIC_DIR = "./lab6/errors-eta-sgd.txt";
    private static String ERRORS_GRADIENT_DIR = "./lab6/errors-eta-grad.txt";
    private static int MAX_ITERS = 100000;
    private static int RULES_COUNT = 10;

    public static void main(String[] args) throws IOException {
        double[] etas = new double[] {0.005, 0.0005, 0.00005};
        List<List<Double>> errorssStochastic = new ArrayList<>();
        List<List<Double>> errorssNonStochastic = new ArrayList<>();
        for (double eta : etas) {
            ANFIS anfis = new ANFIS(eta, RULES_COUNT, MAX_ITERS);
            anfis.fit(true, true);
            errorssStochastic.add(anfis.getErrors());
            anfis = new ANFIS(eta, RULES_COUNT, MAX_ITERS);
            anfis.fit(false, true);
            errorssNonStochastic.add(anfis.getErrors());
        }

        errorsToFile(errorssStochastic, ERRORS_STOCHASTIC_DIR);
        errorsToFile(errorssNonStochastic, ERRORS_GRADIENT_DIR);
    }


}
