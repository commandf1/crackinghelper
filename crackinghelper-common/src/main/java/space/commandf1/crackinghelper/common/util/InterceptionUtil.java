package space.commandf1.crackinghelper.common.util;

import java.util.Optional;

/**
 * @author commandf1
 */
public class InterceptionUtil {
    public static final StackWalker STACK_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static Optional<StackWalker.StackFrame> getCaller(Class<?> interceptedClass) {
        return STACK_WALKER.walk(stackStream -> stackStream
                .dropWhile(frame -> isProxy(frame.getDeclaringClass(), interceptedClass))
                .findFirst());
    }

    private static boolean isProxy(Class<?> currentClass, Class<?> interceptedClass) {
        String className = currentClass.getName();
        return className.equals(interceptedClass.getName()) ||
                className.startsWith("java.lang.reflect.") ||
                className.startsWith("net.bytebuddy.");
    }
}
