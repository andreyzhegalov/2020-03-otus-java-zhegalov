/*
 *
 */
package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    public void ctrTest() {
        assertDoesNotThrow(() -> {
            new Report();
        });
    }

    @Test
    public void headerTest() {
        var report = new Report();
        assertEquals("TESTING RESULT", report.header());
    }

    @Test
    public void reportFooterTest() {
        var report = new Report();
        assertEquals("Total:0 Success:0 Fail:0", report.footer());
    }

    @Test
    public void testSuccessfullTest() {
        var report = new Report();
        report.testSuccessfull();
        assertEquals("Total:1 Success:1 Fail:0", report.footer());
    }

    @Test
    public void testFailedTest() {
        var report = new Report();
        report.testFailed();
        assertEquals("Total:1 Success:0 Fail:1", report.footer());
    }

    @Test
    public void makeTest() {
        var report = new Report();
        report.addLine("Some test result");
        assertEquals("\nTESTING RESULT\nSome test result\nTotal:0 Success:0 Fail:0", report.make());
    }
}
