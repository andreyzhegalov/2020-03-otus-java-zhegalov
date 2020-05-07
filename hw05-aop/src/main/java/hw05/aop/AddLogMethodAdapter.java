package hw05.aop;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AddLogMethodAdapter extends MethodVisitor {
    private String name;
    private int[] paramInst;

    public AddLogMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM7, mv);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParamInst(int[] paramInst) {
        this.paramInst = paramInst;
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
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                        String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 3);
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(ILjava/lang/String;)Ljava/lang/String;", handle,
                "logged param:\u0001 \u0001");

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

}
