package space.commandf1.crackinghelper.common.util;

import lombok.SneakyThrows;
import lombok.val;
import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.StringResource;
import org.codehaus.janino.CompilerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author commandf1
 */
public class RuntimeUtil {
    @SneakyThrows
    public static @NotNull Class<?> loadClassWithSourceCode(@NotNull String sourceCode,
                                                            @NotNull ClassLoader classLoader,
                                                            @NotNull String className) {
        val bytecodeMap = compileToBytecode(className, sourceCode);
        val classBytes = extractClassBytes(bytecodeMap, className);
        val loadedClass = ClassUtil.defineClass(
                classLoader, className, classBytes
        );

        loadInnerClasses(bytecodeMap, classLoader, className);
        return loadedClass;
    }

    private static byte[] extractClassBytes(Map<String, byte[]> bytecodeMap, String className) {
        val classPath = className.replace('.', '/') + ".class";
        val bytecode = bytecodeMap.get(classPath);

        if (bytecode == null) {
            throw new RuntimeException("Cannot find byte codes:" + classPath);
        }

        return bytecode;
    }

    private static Map<String, byte[]> compileToBytecode(String fullClassName, String sourceCode)
            throws Exception {
        val compiler = new CompilerFactory().newCompiler();
        Map<String, byte[]> classBytesMap = new HashMap<>();

        compiler.setClassFileCreator(new MapResourceCreator(classBytesMap));

        val sourceResource = new StringResource(fullClassName + ".java", sourceCode);
        compiler.compile(new Resource[] {sourceResource});

        return classBytesMap;
    }

    private static void loadInnerClasses(Map<String, byte[]> bytecodeMap,
                                         ClassLoader targetClassLoader,
                                         String mainClassName) {
        val mainClassPath = mainClassName.replace('.', '/');

        for (val entry : bytecodeMap.entrySet()) {
            val classPath = entry.getKey();

            if (classPath.equals(mainClassPath + ".class")) {
                continue;
            }

            if (classPath.startsWith(mainClassPath) && classPath.contains("$")) {
                val innerClassName = classPath.substring(0, classPath.length() - 6)
                        .replace('/', '.');

                ClassUtil.defineClass(
                        targetClassLoader, innerClassName, entry.getValue()
                );
            }
        }
    }
}
