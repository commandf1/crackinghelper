package space.commandf1.crackinghelper.common.tracker.trackers;

import lombok.val;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.utility.JavaModule;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.tracker.ITracker;
import space.commandf1.crackinghelper.common.util.InterceptionUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author commandf1
 */
public class MethodTracker implements ITracker<String>, AgentBuilder.Listener {
    private static final Logger logger = IPluginController.getController().getLogger();
    @Override
    public void register(String rawMethodName) {
        val split = rawMethodName.split("#");
        final var className = split[0];
        final var methodName = split[1];

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(this)
                .type(named(className).and(not(isStatic().or(isSynthetic()).or(isFinal()))))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.method(named(methodName)).intercept(MethodDelegation.to(MethodTrackerInterceptor.class))
                )
                .installOn(IPluginController.getController().getInstrumentation());
    }

    @Override
    public void onDiscovery(@NotNull String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }

    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, @NotNull DynamicType dynamicType) {
        logger.info("[METHOD TRACKER] Transformed " + typeDescription.getName());
    }

    @Override
    public void onIgnored(@NotNull TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }

    @Override
    public void onError(@NotNull String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, @NotNull Throwable throwable) {
    }

    @Override
    public void onComplete(@NotNull String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }

    public static class MethodTrackerInterceptor {

        @RuntimeType
        public static Object intercept(@Origin Method method,
                                       @AllArguments Object[] args,
                                       @SuperCall Callable<?> callable) {
            val caller = InterceptionUtil.getCaller(method.getDeclaringClass());

            if (caller.isEmpty()) {
                System.out.println("=============== [ METHOD MONITOR ] ===============");
                System.out.println(" METHOD INVOKED");
                System.out.println(" Method: " + method.getName());
                System.out.println(" Class: " + method.getDeclaringClass());
                System.out.println(" Caller: " + "Unknown");
                System.out.println(" Arguments: " + Arrays.toString(args));
                System.out.println(" TimeMillis: " + System.currentTimeMillis());
                System.out.println("==================================================");
                try {
                    return callable.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            val formattedStackTraceMessage = String.format("%s#%s(%s:%s)",
                    caller.get().getClassName(),
                    caller.get().getMethodName(),
                    caller.get().getFileName(),
                    caller.get().getLineNumber()
            );

            System.out.println("=============== [ METHOD MONITOR ] ===============");
            System.out.println(" METHOD INVOKED");
            System.out.println(" Method: " + method.getName());
            System.out.println(" Class: " + method.getDeclaringClass());
            System.out.println(" Caller: " + formattedStackTraceMessage);
            System.out.println(" Arguments: " + Arrays.toString(args));
            System.out.println(" TimeMillis: " + System.currentTimeMillis());
            System.out.println("==================================================");

            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}