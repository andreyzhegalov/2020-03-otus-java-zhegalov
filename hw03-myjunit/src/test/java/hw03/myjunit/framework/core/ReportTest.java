package hw03.myjunit.framework.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReportTest {

    @Test
    public void ctrTest() {
        assertDoesNotThrow(() -> {
            new Report();
        });
    }

    @Test
    public void headerTest() {
        final var report = new Report();
        assertEquals("TESTING RESULT", report.header());
    }

    @Test
    public void reportFooterTest() {
        final var report = new Report();
        assertEquals("Total:0 Success:0 Fail:0", report.footer());
    }

    @Test
    public void testSuccessfullTest() {
        final var report = new Report();
        report.testSuccessfull();
        assertEquals("Total:1 Success:1 Fail:0", report.footer());
    }

    @Test
    public void testFailedTest() {
        final var report = new Report();
        report.testFailed();
        assertEquals("Total:1 Success:0 Fail:1", report.footer());
    }

    @Test
    public void makeTest() {
        final var report = new Report();
        report.addLine("Some test result");
        assertEquals(
                System.getProperty("line.separator") + "TESTING RESULT" + System.getProperty("line.separator")
                        + "Some test result" + System.getProperty("line.separator") + "Total:0 Success:0 Fail:0",
                report.make());
    }
}
