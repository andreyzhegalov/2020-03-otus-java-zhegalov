package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class AssertTest {
    @Test
    public void failTest(){
        assertThrows(AssertionError.class, ()->{
            Assert.fail();
        });
    }

    @Test
    public void assertTrueTest(){
        assertDoesNotThrow(()->{
            Assert.assertTrue(true);
        });
    }

    @Test
    public void assertTrueTestFail(){
        assertThrows(AssertionError.class, ()->{
            Assert.assertTrue(false);
        });
    }
}

