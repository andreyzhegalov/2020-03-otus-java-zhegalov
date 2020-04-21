package hw03.myjunit;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<String> report;
    private int okTest;
    private int failTest;

    Report() {
        report = new ArrayList<String>();
    }

    void addLine(String line) {
        report.add(line);
    }

    void testSuccessfull() {
        okTest++;
    }

    void testFailed() {
        failTest++;
    }

    String make() {
        var result = new StringBuilder();
        result.append(header());
        result.append(System.getProperty("line.separator"));
        for (var line : report) {
            result.append(line);
            result.append(System.getProperty("line.separator"));
        }
        result.append(footer());
        return result.toString();
    }

    String footer() {
        return String.format("Total:%d Success:%d Fail:%d", okTest + failTest, okTest, failTest);
    }

    String header() {
        return "Testing Result";
    }
}
