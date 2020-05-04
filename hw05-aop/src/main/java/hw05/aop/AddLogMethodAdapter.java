package hw05.aop;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddLogMethodAdapter extends MethodVisitor{

    public AddLogMethodAdapter(MethodVisitor mv) {
         super(Opcodes.ASM7, mv);
     }

    @Override
    public void visitCode() {
        // TODO Auto-generated method stub

        mv.visitCode();
        //mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //mv.visitVarInsn(Opcodes.ILOAD, 1);
        //mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
    }
}

