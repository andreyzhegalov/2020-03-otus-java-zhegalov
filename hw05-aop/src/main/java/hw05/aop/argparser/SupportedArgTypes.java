package hw05.aop.argparser;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.objectweb.asm.Opcodes;

class SupportedArgTypes {
    private final static Map<String, ArgType> ARGVARIANTS =
        Stream.of(new Object[][] {
                { "Z", new ArgType(Opcodes.ILOAD, 1, "Z")},
                { "B", new ArgType(Opcodes.ILOAD, 1, "B")},
                { "C", new ArgType(Opcodes.ILOAD, 1, "C")},
                { "S", new ArgType(Opcodes.ILOAD, 1, "S")},
                { "I", new ArgType(Opcodes.ILOAD, 1, "I")},
                { "J", new ArgType(Opcodes.LLOAD, 2, "J")},
                { "F", new ArgType(Opcodes.FLOAD, 1, "F")},
                { "D", new ArgType(Opcodes.DLOAD, 2, "D")},
                { "Ljava/lang/String;", new ArgType(Opcodes.ALOAD, 1, "Ljava/lang/String;")}
        })
        .collect(Collectors.collectingAndThen(Collectors.toMap(data -> (String)data[0], data -> (ArgType)data[1]),
                Collections::<String, ArgType>unmodifiableMap));

    public static ArgType get(final String typeDesc) {
        return ARGVARIANTS.get(typeDesc);
    }
}
