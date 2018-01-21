package hr.fer.zemris.neurofuzzy.reports;

import hr.fer.zemris.neurofuzzy.ANFIS;
import hr.fer.zemris.neurofuzzy.support.Rule;

import java.io.IOException;
import java.util.List;

import static hr.fer.zemris.neurofuzzy.reports.helper.ReportUtils.rulesToFile;

public class RulesReportGenerator {

    private static String RULES_DIR = "./lab6/rules.txt";
    private static int MAX_ITERS = 100000;
    private static int RULES_COUNT = 10;
    private static double ETA = 0.0005;

    public static void main(String[] args) throws IOException {
        ANFIS anfis = new ANFIS(ETA, RULES_COUNT, MAX_ITERS);
        anfis.fit(false, true);
        List<Rule> rules = anfis.getRules();
        rulesToFile(rules, RULES_DIR);
    }
}
