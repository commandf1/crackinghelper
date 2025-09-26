package space.commandf1.crackinghelper.bungee.convertor.plugin;

import net.md_5.bungee.api.plugin.Plugin;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.PluginDescription;

/**
 * @author commandf1
 */
public class BungeePlugin extends CommonPlugin<Plugin> {
    public BungeePlugin(Plugin plugin) {
        super(plugin);
    }

    @Override
    public PluginDescription getDescription() {
        return new PluginDescription(
                this.getPlugin().getDescription().getName(),
                this.getPlugin().getDescription().getMain(),
                this.getPlugin().getDescription().getVersion()
        );
    }

    @Override
    public String getName() {
        return this.getPlugin().getDescription().getName();
    }
}
