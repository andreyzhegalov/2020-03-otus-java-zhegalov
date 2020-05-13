package hw05.aop.argparser;

import java.util.Map;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;

class SupportedArgTypes {
    private final Map<String, ArgType> argVariants;

    public SupportedArgTypes() {
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

    public ArgType get(final String typeDesc) {
        return argVariants.get(typeDesc);
    }
}
