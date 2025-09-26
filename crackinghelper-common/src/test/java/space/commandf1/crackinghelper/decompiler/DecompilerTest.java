package space.commandf1.crackinghelper.decompiler;

import org.junit.jupiter.api.Test;
import space.commandf1.crackinghelper.common.util.ClassUtil;

import java.util.Arrays;

public class DecompilerTest {
    @Test
    public void decompileTest() {
        System.out.println("ClassUtil.decompile(Object.class) = " + ClassUtil.decompile(Object.class));
    }

    @Test
    public void asByteArrayTest() {
        System.out.println("ClassUtil.getClassBytes(DecompilerTest.class) = " +
                Arrays.toString(ClassUtil.getClassBytes(DecompilerTest.class)));
    }
}
