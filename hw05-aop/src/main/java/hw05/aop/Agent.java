package hw05.aop;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                final ClassReader classReader = new ClassReader(classfileBuffer);
                final ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                final ClassVisitor classVisitor = new AddLogClassAdapter(classWriter);
                classReader.accept(classVisitor, 0);
                return classWriter.toByteArray();
            }
        });

    }
}
