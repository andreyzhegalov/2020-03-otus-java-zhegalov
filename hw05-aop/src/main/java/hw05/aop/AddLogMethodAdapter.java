package hw05.aop;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import hw05.aop.argparser.ArgTypeParser;

public class AddLogMethodAdapter extends MethodVisitor {
    private final String name;
    private final ArgTypeParser argTypeParser;

    public AddLogMethodAdapter(String name, String description, MethodVisitor mv) {
        super(Opcodes.ASM7, mv);
        this.name = name;
        argTypeParser = new ArgTypeParser(description);
    }

    @Override
    public void visitCode() {
        final Handle handle = new Handle(
                Opcodes.H_INVOKESTATIC, Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants", MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                        String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
        saveClassNameToFrameLocals();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        loadParamToStack();
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", getConcatMethodSignature(), handle, getOutLogMessage());
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    private void saveClassNameToFrameLocals() {
        mv.visitLdcInsn(this.name);
        mv.visitVarInsn(Opcodes.ASTORE, argTypeParser.getFullSlotSize() + 1);
    }

    private String getConcatMethodSignature() {
        final String stringTypeDesc = "Ljava/lang/String;";
        final String concatMethodSignature = String.format("(%s)Ljava/lang/String;",
                (stringTypeDesc + argTypeParser.getFullDescription()));
        return concatMethodSignature;
    }

    private String getOutLogMessage() {
        final StringBuilder logMesssageBuilder = new StringBuilder();
        logMesssageBuilder.append("Method name \u0001 ");
        for (int i = 0; i < argTypeParser.getArgs().size(); i++) {
            logMesssageBuilder.append(" param \u0001");
        }
        return logMesssageBuilder.toString();
    }

    private void loadParamToStack() {
        mv.visitVarInsn(Opcodes.ALOAD, argTypeParser.getFullSlotSize() + 1); // method name
        int curSlot = 1; // 0 for this
        for (final var arg : argTypeParser.getArgs()) {
            mv.visitVarInsn(arg.getLoadOpcode(), curSlot);
            curSlot += arg.getSlotSize();
        }
    }

}
