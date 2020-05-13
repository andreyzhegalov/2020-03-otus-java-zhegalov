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
        if (args.length < 1) {
            throw new RuntimeException("Set path to .class file");
        }
        final var data = loadClass(args[0]);
        asmModifications(data, args[0]);
    }

    private static byte[] loadClass(final String className) throws IOException {
        final File file = new File(className);
        return Files.readAllBytes(file.toPath());
    }

    private static void asmModifications(final byte[] classArray, final String outFile) {
        final ClassReader classReader = new ClassReader(classArray);
        final ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        final ClassVisitor classVisitor = new AddLogClassAdapter(classWriter);
        classReader.accept(classVisitor, 0);

        final byte[] finalClass = classWriter.toByteArray();
        verifyAndPrint(finalClass);
        writeToFile(finalClass, outFile);
    }

    private static void writeToFile(final byte[] data, final String fileName) {
        try (OutputStream fos = new FileOutputStream(fileName)) {
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verifyAndPrint(final byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
        final ClassVisitor checker = new CheckClassAdapter(tracer, true);
        reader.accept(checker, 0);
    }
}
