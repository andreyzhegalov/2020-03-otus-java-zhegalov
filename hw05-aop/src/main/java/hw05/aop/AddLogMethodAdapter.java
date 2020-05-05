package hw05.aop;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddLogMethodAdapter extends MethodVisitor {
    private String name;

    public AddLogMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM7, mv);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.out.println( "calling visitAttribute" );
        System.out.println(attribute.toString());
        mv.visitAttribute(attribute);
    }

    @Override
    public void visitParameter(String name, int access) {
        System.out.println( "calling visitParameter" );
        System.out.println( name );
        mv.visitParameter(name, access);
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        System.out.println( "calling visitFrame" );
        mv.visitFrame(type, numLocal, local, numStack, stack);
    }

    @Override
    public void visitEnd() {
        System.out.println( "calling visitEnd" );
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

        // TODO Auto-generated method stub

        mv.visitCode();
        // Should be message executed method: calculation, param: 6
        //
        // mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
        // "Ljava/io/PrintStream;");
        // mv.visitVarInsn(Opcodes.ILOAD, 1);
        // mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
        // "(I)V", false);
    }

}
