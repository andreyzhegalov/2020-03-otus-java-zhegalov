package hw05.aop.argparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "Z"));

        final var parseResult = new ArgTypeParser("(Z)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testByteParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "B"));

        final var parseResult = new ArgTypeParser("(B)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testCharParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "C"));

        final var parseResult = new ArgTypeParser("(C)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testShortParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "S"));

        final var parseResult = new ArgTypeParser("(S)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testIntegerParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "I"));

        final var parseResult = new ArgTypeParser("(I)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testLongParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.LLOAD, 2, "J"));

        final var parseResult = new ArgTypeParser("(J)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testFloatParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.FLOAD, 1, "F"));

        final var parseResult = new ArgTypeParser("(F)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testDoubleParam() {
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.DLOAD, 2, "D"));

        final var parseResult = new ArgTypeParser("(D)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testStringParam() {
        var result = new ArrayList<ArgType>();
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
        var result = new ArrayList<ArgType>();
        result.add(new ArgType(Opcodes.ILOAD, 1, "S"));
        result.add(new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));
        result.add(new ArgType(Opcodes.FLOAD, 1, "F"));
        result.add(new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));
        result.add(new ArgType(Opcodes.ILOAD, 1, "I"));

        final var parseResult = new ArgTypeParser("(SLjava/lang/String;FLjava/lang/String;I)V").getArgs();
        assertTrue(result.equals(parseResult));
    }

    @Test
    public void testGetFullDescription() {
        final var argParser = new ArgTypeParser("(SLjava/lang/String;FLjava/lang/String;I)V");
        assertEquals("SLjava/lang/String;FLjava/lang/String;I", argParser.getFullDescription());
    }

    @Test
    public void testGetFullDescriptionVar2() {
        final var argParser = new ArgTypeParser("(Ljava/lang/String;F)V");
        assertEquals("Ljava/lang/String;F", argParser.getFullDescription());
    }

    @Test
    public void testGetFullSlotSize() {
        final var argParser = new ArgTypeParser("(SLjava/lang/String;FLjava/lang/String;I)V");
        assertEquals(5, argParser.getFullSlotSize());
    }

    @Test
    public void testGetFullSlotSizeVer2() {
        final var argParser = new ArgTypeParser("(Ljava/lang/String;D)V");
        assertEquals(3, argParser.getFullSlotSize());
    }

}
