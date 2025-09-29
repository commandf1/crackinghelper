package space.commandf1.crackinghelper.common.util.processor;

import java.util.function.BiConsumer;

/**
 * @author commandf1
 */
public interface IProcessor<K, V> {
    IProcessor<K, V> after(BiConsumer<K, V> action);

    void process();
}
