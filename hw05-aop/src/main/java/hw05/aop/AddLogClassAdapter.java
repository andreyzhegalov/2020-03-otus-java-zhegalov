package hw05.aop;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AddLogClassAdapter extends ClassVisitor {

	public AddLogClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM7, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv;
        mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //TODO added annotation handler
        if(mv !=null && !name.equals("<init>")){
            var customMethodAdapter = new AddLogMethodAdapter(mv);
            customMethodAdapter.setName(name);
            mv = customMethodAdapter;
        }
        return mv;
    }

}
