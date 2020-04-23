package hw03.myjunit.internal;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<String> report;
    private int okTest;
    private int failTest;

    Report() {
        report = new ArrayList<>();
    }

    public void addLine(final String line) {
        report.add(line);
    }

    public void testSuccessfull() {
        okTest++;
    }

    public void testFailed() {
        failTest++;
    }

    public String make() {
        final var result = new StringBuilder();
        result.append(System.getProperty("line.separator"));
        result.append(header());
        result.append(System.getProperty("line.separator"));
        for (final var line : report) {
            result.append(line);
            result.append(System.getProperty("line.separator"));
        }
        result.append(footer());
        return result.toString();
    }

    public String footer() {
        return String.format("Total:%d Success:%d Fail:%d", okTest + failTest, okTest, failTest);
    }

    public String header() {
        return "TESTING RESULT";
    }
}
