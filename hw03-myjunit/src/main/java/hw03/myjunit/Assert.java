package hw03.myjunit;

public class Assert {

    public static void assertTrue(boolean condition) {
        if (!condition) {
            fail("AssertionFailedError: expected: <true> but was: <false>");
        }
    }

    public static void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }
}
