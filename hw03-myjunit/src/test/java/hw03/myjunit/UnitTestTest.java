/*
 *
 */
package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestTest {
    @Test
    public void testM() {
        var test = new UnitTest();
        assertTrue( test.m1() );
    }
}

