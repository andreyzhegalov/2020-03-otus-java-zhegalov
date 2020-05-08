package hw05.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.objectweb.asm.Opcodes;

public class ParameterToInstConverter {
    private final Vector<ArgType> args;
    private final String description;

    private class ArgSet {
        private final Map<String, ArgType> argVariants;

        public ArgSet() {
            argVariants = new TreeMap<>();
            argVariants.put("Z", new ArgType(Opcodes.ILOAD, 1, "Z"));
            argVariants.put("B", new ArgType(Opcodes.ILOAD, 1, "B"));
            argVariants.put("C", new ArgType(Opcodes.ILOAD, 1, "C"));
            argVariants.put("S", new ArgType(Opcodes.ILOAD, 1, "S"));
            argVariants.put("I", new ArgType(Opcodes.ILOAD, 1, "I"));
            argVariants.put("J", new ArgType(Opcodes.LLOAD, 2, "J"));
            argVariants.put("F", new ArgType(Opcodes.FLOAD, 1, "F"));
            argVariants.put("D", new ArgType(Opcodes.DLOAD, 2, "D"));
            argVariants.put("Ljava/lang/String;", new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;"));
        }

        public ArgType get(String typeDesc) {
            return argVariants.get(typeDesc);
        }
    }

    public ParameterToInstConverter(String description) {
        this.description = description;
        args = new Vector<>();
        parse();
    }

    private void parse() {
        var body = getBoby(description);

        for (int i = 0; i < body.length(); i++) {
            final char curSymbol = body.charAt(i);
            String typeDesc = String.valueOf(curSymbol);
            if (isStartObjectDesc(curSymbol)) {
                var semicolonInd = getEndIndexOfObjectDesc(i, body);
                typeDesc = body.substring(i, semicolonInd + 1); // include ;
                i = semicolonInd;
            }
            var argType = new ArgSet().get(typeDesc);
            if(null == argType){
                throw new IllegalArgumentException("Unsuported arg type");
            }
            args.add(argType);
        }
    }

    static private int getEndIndexOfObjectDesc(int startInd, String body) {
        var semicolonInd = body.indexOf(';', startInd);
        if (-1 == semicolonInd) {
            throw new IllegalArgumentException("Wrong Object type description");
        }
        return semicolonInd;
    }

    static private boolean isStartObjectDesc(char symbol) {
        return 'L' == symbol;
    }

    Vector<ArgType> getArgs() {
        return args;
    }

    static private String getBoby(String description) {
        var startPos = description.indexOf('(');
        var endPos = description.indexOf(')');
        if ((-1 == startPos) || (-1 == endPos)) {
            throw new IllegalArgumentException("wrong method signature");
        }
        return description.substring(startPos + 1, endPos);
    }
}

class ArgType {
    private final int loadOpcode;
    private final short slotSize;
    private final String typeDesc;

    public ArgType(int loadOpcode, int slotSize, String typeDesc) {
        this.loadOpcode = loadOpcode;
        this.slotSize = (short) slotSize;
        this.typeDesc = typeDesc;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) loadOpcode;
        result = 31 * result + (int) slotSize;
        result = 31 * result + (typeDesc != null ? typeDesc.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ArgType object = (ArgType) o;

        if (loadOpcode != object.loadOpcode)
            return false;
        if (slotSize != object.slotSize)
            return false;
        return !(typeDesc != null ? !typeDesc.equals(object.typeDesc) : object.typeDesc != null);
    }

    @Override
    public String toString() {
        return "ArgType{" +
            "loadOpcode = " + loadOpcode +
            ", slotSize = " + slotSize +
            ", typeDesc = " + typeDesc +
            "}";
    }

}
