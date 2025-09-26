package space.commandf1.crackinghelper.common.convertor.plugin;

import space.commandf1.crackinghelper.common.convertor.ItemHolder;

/**
 * @author commandf1
 */
public abstract class CommonPlugin<T> extends ItemHolder<T> {
    static IPluginController controller = null;

    public static void registerController(IPluginController controller) {
        CommonPlugin.controller = controller;
    }

    public CommonPlugin(T plugin) {
        super(plugin);
    }

    public final T getPlugin() {
        return this.getItem();
    }

    public abstract PluginDescription getDescription();

    public ClassLoader getPluginClassLoader() {
        return this.getPlugin().getClass().getClassLoader();
    }

    public abstract String getName();
}
