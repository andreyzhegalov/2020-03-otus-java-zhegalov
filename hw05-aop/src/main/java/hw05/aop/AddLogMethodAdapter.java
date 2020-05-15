package hw05.aop;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import hw05.aop.argparser.ArgTypeParser;

public class AddLogMethodAdapter extends MethodVisitor {
    private final String name;
    private final String description;
    private boolean hasLogAnnotation;

    public AddLogMethodAdapter(final String name, final String description, final MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor);
        this.name = name;
        this.description = description;
    }

    @Override
    public void visitCode() {
        if (!hasLogAnnotation) {
            mv.visitCode();
            return;
        }
        final var argTypeParser = new ArgTypeParser(description);

        final Handle handle = new Handle(
                Opcodes.H_INVOKESTATIC, Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants", MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                        String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
        final int methodNameSlot = argTypeParser.getFullSlotSize() + 1;
        saveMethodNameToFrameLocals(methodNameSlot);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        loadMethodNameToStack(methodNameSlot);
        loadParamToStack(argTypeParser);
        mv.visitInvokeDynamicInsn("makeConcatWithConstants",
                getConcatMethodSignature(argTypeParser.getFullDescription()), handle, getOutLogMessage(argTypeParser));
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        hasLogAnnotation = false;
        if (descriptor.contains("Log")) {
            hasLogAnnotation = true;
            return null; // remove log annotation
        }
        return mv.visitAnnotation(descriptor, visible);
    }

    private void saveMethodNameToFrameLocals(final int slotNumber) {
        mv.visitLdcInsn(this.name);
        mv.visitVarInsn(Opcodes.ASTORE, slotNumber);
    }

    private void loadMethodNameToStack(final int slotNumber) {
        mv.visitVarInsn(Opcodes.ALOAD, slotNumber);
    }

    private String getConcatMethodSignature(final String fullArgDesription) {
        final String stringTypeDesc = "Ljava/lang/String;"; // for method name
        return String.format("(%s)Ljava/lang/String;",
                stringTypeDesc + fullArgDesription);
    }

    private String getOutLogMessage(final ArgTypeParser argTypeParser) {
        return "Method name \u0001 "+
            " param \u0001".repeat(argTypeParser.getArgs().size()) ;
    }

    private void loadParamToStack(final ArgTypeParser argTypeParser) {
        int curSlot = 1; // 0 for this
        for (final var arg : argTypeParser.getArgs()) {
            mv.visitVarInsn(arg.getLoadOpcode(), curSlot);
            curSlot += arg.getSlotSize();
        }
    }

}
