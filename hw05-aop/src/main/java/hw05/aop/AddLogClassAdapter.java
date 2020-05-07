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
        System.out.println(desc);
        MethodVisitor mv;
        mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //TODO added annotation handler
        if( name.equals("testMethod")){
            var customMethodAdapter = new AddLogMethodAdapter(mv);
            customMethodAdapter.setName(name);
            //customMethodAdapter.setParametrs();
            mv = customMethodAdapter;
        }
        return mv;
    }

}
