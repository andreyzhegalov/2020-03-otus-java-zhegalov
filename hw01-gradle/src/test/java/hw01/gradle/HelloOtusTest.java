/*
 *
 */
package hw01.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloOtusTest {

    @Test
    public void testGetFactorialFromNull() {
        HelloOtus helloOtus= new HelloOtus(0);
        assertEquals(1, helloOtus.getFactorial());
    }

    @Test
    public void testGetFactorialFromNumber() {
        HelloOtus helloOtus= new HelloOtus(3);
        assertEquals(1*2*3, helloOtus.getFactorial());
    }
}
