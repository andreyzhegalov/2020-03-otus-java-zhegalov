package hw05.aop.argparser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Vector;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

public class ArgTypeParserTest {

    @Test
    public void testWrongMethodSignatureNoOpeningBracket() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ArgTypeParser("V)V").getArgs();
        });
    }

    @Test
    public void testWrongMethodSignatureNoClosingBracket() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ArgTypeParser("(VV").getArgs();
        });
    }

    @Test
    public void testBooleanParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "Z"));

        final var parseResult = new ArgTypeParser("(Z)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testByteParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "B"));

        final var parseResult = new ArgTypeParser("(B)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testCharParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "C"));

        final var parseResult = new ArgTypeParser("(C)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testShortParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "S"));

        final var parseResult = new ArgTypeParser("(S)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testIntegerParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "I"));

        final var parseResult = new ArgTypeParser("(I)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testLongParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.LLOAD, 2, "J"));

        final var parseResult = new ArgTypeParser("(J)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testFloatParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.FLOAD, 1, "F"));

        final var parseResult = new ArgTypeParser("(F)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testDoubleParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.DLOAD, 2, "D"));

        final var parseResult = new ArgTypeParser("(D)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testStringParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));

        final var parseResult = new ArgTypeParser("(Ljava/lang/String;)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testStringParamNoClosingSemicolon() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ArgTypeParser("(Ljava/lang/String)V").getArgs();
        });
    }

    @Test
    public void testArrayParam() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ArgTypeParser("([I)V").getArgs();
        });
    }

    @Test
    public void testCombinationParam() {
        var result = new Vector<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "S"));
        result.add(new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));
        result.add(new ArgType(Opcodes.FLOAD, 1, "F"));
        result.add(new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));
        result.add(new ArgType(Opcodes.ILOAD, 1, "I"));

        final var parseResult = new ArgTypeParser("(SLjava/lang/String;FLjava/lang/String;I)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

}
