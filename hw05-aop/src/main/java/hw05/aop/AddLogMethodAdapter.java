package hw05.aop;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import hw05.aop.argparser.ArgTypeParser;

public class AddLogMethodAdapter extends MethodVisitor {
    private final String name;
    private final String description;
    private boolean hasLogAnnotation;

    public AddLogMethodAdapter(String name, String description, MethodVisitor mv) {
        super(Opcodes.ASM7, mv);
        this.name = name;
        this.description = description;
    }

    @Override
    public void visitCode() {
        System.out.println("visitCode " + name);

        if (!hasLogAnnotation)
        {
            mv.visitCode();
            return;
        }

        System.out.println("Added log string");
        var argTypeParser = new ArgTypeParser(description);

        final Handle handle = new Handle(
                Opcodes.H_INVOKESTATIC, Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants", MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                        String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
        saveClassNameToFrameLocals(argTypeParser);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        loadParamToStack(argTypeParser);
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", getConcatMethodSignature(argTypeParser), handle, getOutLogMessage(argTypeParser));
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("visitAnnotation" + descriptor);

        hasLogAnnotation = false;
        if (descriptor.contains("Log")){
            hasLogAnnotation = true;
            return null; // remove log annotation
        }

        return mv.visitAnnotation(descriptor, visible);
    }

    private void saveClassNameToFrameLocals(ArgTypeParser argTypeParser) {
        mv.visitLdcInsn(this.name);
        mv.visitVarInsn(Opcodes.ASTORE, argTypeParser.getFullSlotSize() + 1);
    }

    private String getConcatMethodSignature(ArgTypeParser argTypeParser) {
        final String stringTypeDesc = "Ljava/lang/String;";
        final String concatMethodSignature = String.format("(%s)Ljava/lang/String;",
                (stringTypeDesc + argTypeParser.getFullDescription()));
        return concatMethodSignature;
    }

    private String getOutLogMessage(ArgTypeParser argTypeParser) {
        final StringBuilder logMesssageBuilder = new StringBuilder();
        logMesssageBuilder.append("Method name \u0001 ");
        for (int i = 0; i < argTypeParser.getArgs().size(); i++) {
            logMesssageBuilder.append(" param \u0001");
        }
        return logMesssageBuilder.toString();
    }

    private void loadParamToStack(ArgTypeParser argTypeParser) {
        mv.visitVarInsn(Opcodes.ALOAD, argTypeParser.getFullSlotSize() + 1); // method name
        int curSlot = 1; // 0 for this
        for (final var arg : argTypeParser.getArgs()) {
            mv.visitVarInsn(arg.getLoadOpcode(), curSlot);
            curSlot += arg.getSlotSize();
        }
    }

}
