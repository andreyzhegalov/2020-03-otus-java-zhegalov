package hw05.aop;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

public class ParameterToInstConverter {
    private final List<Integer> insts;
    private boolean inBody;
    private boolean inObjDesc;
    private final String description;

    public ParameterToInstConverter(String description) {
        this.description = description;
        insts = new ArrayList<>();
        inBody = false;

        parse();
    }

    private void parse() {
        insts.clear();

        for (int i = 0; i < description.length(); i++) {
            final char curSymbol = description.charAt(i);

            if (isBodyEnd(curSymbol)) {
                return;
            }

            if (!isBody(curSymbol)) {
                continue;
            }

            if (isObjectDescription(curSymbol)) {
                continue;
            }

            final var inst = typeConverter(curSymbol);
            insts.add(inst);
        }
    }

    public int[] getInsts() {
        var result = new int[insts.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = insts.get(i);
        }
        return result;
    }

    private boolean isBody(char symbol) {
        if (isBodyStart(symbol)) {
            inBody = true;
            return false;
        }
        if (isBodyEnd(symbol)) {
            inBody = false;
        }
        return inBody;
    }

    private static boolean isBodyStart(char symbol) {
        return '(' == symbol;
    }

    private static boolean isBodyEnd(char symbol) {
        return ')' == symbol;
    }

    private boolean isObjectDescription(char symbol) {
        if (isObjectType(symbol)) {
            inObjDesc = true;
            return false;
        }
        if (isObjectDescEnd(symbol)) {
            inObjDesc = false;
            return true;
        }

        return inObjDesc;
    }

    private static boolean isObjectType(char symbol) {
        return 'L' == symbol;
    }

    private static boolean isObjectDescEnd(char symbol) {
        return ';' == symbol;
    }

    private static int typeConverter(char symbol) {
        switch (symbol) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                return Opcodes.ILOAD;
            case 'J':
                return Opcodes.LLOAD;
            case 'F':
                return Opcodes.FLOAD;
            case 'D':
                return Opcodes.DLOAD;
            case 'L':
                return Opcodes.ALOAD;
            default:
                throw new IllegalArgumentException("Wrong argument type in the description");
        }
    }

}
