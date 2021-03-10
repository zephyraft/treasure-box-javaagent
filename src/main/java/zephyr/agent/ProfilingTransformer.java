package zephyr.agent;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ProfilingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?>
            classBeingRedefined, ProtectionDomain protectionDomain, byte[]
                                    classfileBuffer) throws IllegalClassFormatException {
        try {
//            System.out.println(className);
            // 排除一些不需要处理的方法
            if (ProfilingFilter.isNotNeedInject(className)) {
                return classfileBuffer;
            }
            byte[] bytes = getBytes(loader, className, classfileBuffer);

//            ByteCodeUtils.writeToFile(bytes, "build/classes/java/main/zephyr/agent/t.class");
            return bytes;
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        return classfileBuffer;
    }

    private byte[] getBytes(ClassLoader loader, String className, byte[]
            classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ProfilingClassAdapter(cw, className);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }

    static class ProfilingClassAdapter extends ClassVisitor {
        public ProfilingClassAdapter(final ClassVisitor cv, String innerClassName) {
            super(Opcodes.ASM9, cv);
        }

        public MethodVisitor visitMethod(int access,
                                         String name,
                                         String desc,
                                         String signature,
                                         String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc,
                    signature, exceptions);
            return new ProfilingMethodVisitor(mv, access, name, desc);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter {
        private final String methodName;

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int
                access, String name, String descriptor) {
            super(Opcodes.ASM9, methodVisitor, access, name, descriptor);
            this.methodName = name;
        }

        // 方法进入时
        @Override
        protected void onMethodEnter() {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
                    "Ljava/io/PrintStream;");
            mv.visitLdcInsn("method enter");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);
        }

        // 方法退出时
        @Override
        protected void onMethodExit(int opcode) {
        }
    }
}
