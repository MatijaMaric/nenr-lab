package hr.fer.zemris.neurofuzzy.reports.helper;

import hr.fer.zemris.neurofuzzy.support.Rule;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportUtils {

    public static void errorsToFile(List<List<Double>> errorss, String errorsDir) throws IOException {

        Writer w = Files.newBufferedWriter(Paths.get(errorsDir), StandardCharsets.UTF_8);

        for (int i = 0; i < errorss.get(0).size(); ++i) {
            for (List<Double> errors : errorss) {
                w.write(errors.get(i) + " ");
            }
            w.write("\n");
        }

        w.flush();
        w.close();

    }

    public static void rulesToFile(List<Rule> rules, String rulesDir) throws IOException {

        Writer w = Files.newBufferedWriter(Paths.get(rulesDir), StandardCharsets.UTF_8);

        for (Rule rule : rules) {
            w.write(rule.a + " " + rule.b + " " + rule.c + " " + rule.d + "\n");
        }

        w.flush();
        w.close();
    }
}
