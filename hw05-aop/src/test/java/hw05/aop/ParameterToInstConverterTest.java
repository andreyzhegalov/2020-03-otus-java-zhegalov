package hw05.aop;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

public class ParameterToInstConverterTest {

    @Test
    public void testBooleanParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD }, new ParameterToInstConverter("(Z)V").getInsts());
    }

    @Test
    public void testByteParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD }, new ParameterToInstConverter("(B)V").getInsts());
    }

    @Test
    public void testCharParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD }, new ParameterToInstConverter("(C)V").getInsts());
    }

    @Test
    public void testShortParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD }, new ParameterToInstConverter("(S)V").getInsts());
    }

    @Test
    public void testIntegerParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD }, new ParameterToInstConverter("(I)V").getInsts());
    }

    @Test
    public void testLongParam() {
        assertArrayEquals(new int[] { Opcodes.LLOAD }, new ParameterToInstConverter("(J)V").getInsts());
    }

    @Test
    public void testFloatParam() {
        assertArrayEquals(new int[] { Opcodes.FLOAD }, new ParameterToInstConverter("(F)V").getInsts());
    }

    @Test
    public void testDoubleParam() {
        assertArrayEquals(new int[] { Opcodes.DLOAD }, new ParameterToInstConverter("(D)V").getInsts());
    }

    @Test
    public void testObjectParam() {
        assertArrayEquals(new int[] { Opcodes.ALOAD },
                new ParameterToInstConverter("(Ljava/lang/Object;)V").getInsts());
    }

    @Test
    public void testArrayParam() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ParameterToInstConverter("([I])V").getInsts();
        });
    }

    @Test
    public void testCombinationParam() {
        assertArrayEquals(new int[] { Opcodes.ILOAD, Opcodes.ALOAD, Opcodes.FLOAD },
                new ParameterToInstConverter("(ILjava/lang/Object;F)V").getInsts());
    }

}
