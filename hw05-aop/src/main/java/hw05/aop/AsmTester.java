package hw05.aop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

public class AsmTester {
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            throw new RuntimeException("Set path to .class file");
        }
        var ba = loadClass( args[0] );
        asmModifications(ba, args[0]);
    }

    static byte[] loadClass(String className) throws IOException {
        File file = new File(className);
        byte[] bytecode = Files.readAllBytes(file.toPath());
        return bytecode;
    }

    static void asmModifications(byte[] classArray, String outFile) {
        ClassReader cr = new ClassReader(classArray);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new AddLogClassAdapter(cw);
        cr.accept(cv, 0);

        byte[] finalClass = cw.toByteArray();
        verifyAndPrint(finalClass);
        writeToFile(finalClass, outFile);
    }

    static void writeToFile(byte[] data, String fileName) {
        try (OutputStream fos = new FileOutputStream(fileName)) {
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void verifyAndPrint(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
        ClassVisitor checker = new CheckClassAdapter(tracer, true);
        reader.accept(checker, 0);
    }
}
