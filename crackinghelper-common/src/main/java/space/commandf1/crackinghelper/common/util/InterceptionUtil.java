package space.commandf1.crackinghelper.common.util;

import org.jetbrains.annotations.NotNull;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Optional;
import java.util.Set;

/**
 * @author commandf1
 */
public class InterceptionUtil {
    public static final StackWalker STACK_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static Optional<StackWalker.StackFrame> getCaller(@NotNull Class<?> interceptedClass) {
        return STACK_WALKER.walk(stackStream -> stackStream
                .dropWhile(frame -> isProxy(frame.getDeclaringClass(), interceptedClass))
                .findFirst());
    }

    public static void retransformClasses(@NotNull Set<Class<?>> classes,
                                          @NotNull ClassFileTransformer transformer,
                                          @NotNull Instrumentation instrumentation) {
        instrumentation.addTransformer(transformer, true);
        try {
            classes.forEach(clazz -> {
                if (!ClassUtil.isLambda(clazz) && instrumentation.isModifiableClass(clazz)) {
                    try {
                        instrumentation.retransformClasses(clazz);
                    } catch (UnmodifiableClassException e) {
                        System.out.println("Error retransforming class " + clazz.getName() + ": " + e.getMessage());
                    }
                }
            });
        } finally {
            instrumentation.removeTransformer(transformer);
        }
    }

    private static boolean isProxy(@NotNull Class<?> currentClass,
                                   @NotNull Class<?> interceptedClass) {
        String className = currentClass.getName();
        return className.equals(interceptedClass.getName()) ||
                className.startsWith("java.lang.reflect.") ||
                className.startsWith("net.bytebuddy.");
    }
}
