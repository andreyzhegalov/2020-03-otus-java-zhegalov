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
    public void visitAttribute(Attribute attribute) {
        System.out.println("calling visitAttribute");
        System.out.println(attribute.toString());
        mv.visitAttribute(attribute);
    }

    @Override
    public void visitParameter(String name, int access) {
        System.out.println("calling visitParameter");
        System.out.println(name);
        mv.visitParameter(name, access);
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        System.out.println("calling visitFrame");
        mv.visitFrame(type, numLocal, local, numStack, stack);
    }

    @Override
    public void visitEnd() {
        System.out.println("calling visitEnd");
        mv.visitEnd();
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("calling visitInsn");
        mv.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        System.out.println("calling visitMaxs" + maxStack + " " + maxLocals);
        System.out.println("Method name " + name);
        mv.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitCode() {
        System.out.println("calling visitCode");
        Handle handle = new Handle(
                Opcodes.H_INVOKESTATIC, Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants", MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                        String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);

        // Save class name
        mv.visitLdcInsn(this.name);
        mv.visitVarInsn(Opcodes.ASTORE, argTypeParser.getFullSlotSize() + 1);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        // Load param to stack
        mv.visitVarInsn(Opcodes.ALOAD, argTypeParser.getFullSlotSize() + 1);
        int curSlot = 1; // 0 for this
        for (final var arg : argTypeParser.getArgs()) {
            mv.visitVarInsn(arg.getLoadOpcode(), curSlot);
            curSlot += arg.getSlotSize();
        }

        // Make concat method signature
        String stringTypeDesc = "Ljava/lang/String;";
        String concatMethodSignature = String.format("(%s)Ljava/lang/String;",
                (stringTypeDesc + argTypeParser.getFullDescription()));
        System.out.println(concatMethodSignature);

        // Make log message
        StringBuilder logMesssageBuilder = new StringBuilder();
        logMesssageBuilder.append("Method name ");
        logMesssageBuilder.append("\u0001 ");
        for (int i = 0; i < argTypeParser.getArgs().size(); i++) {
            logMesssageBuilder.append(" param \u0001");
        }
        String logMessage = logMesssageBuilder.toString();
        System.out.println(logMessage);

        // invoke concat method
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", concatMethodSignature, handle, logMessage);

        // invoke println whith result concat method
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

}
