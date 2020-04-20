package hw03.myjunit;

public class Assert {

    public static void assertTrue(boolean condition) {
        if (!condition) {
            fail();
        }
    }

    public static void fail() {
        throw new AssertionError();
    }
}
