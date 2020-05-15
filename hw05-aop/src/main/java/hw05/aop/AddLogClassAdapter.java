package hw05.aop;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddLogClassAdapter extends ClassVisitor {

    public AddLogClassAdapter(final ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new AddLogMethodAdapter(name, desc, methodVisitor);
    }

}
