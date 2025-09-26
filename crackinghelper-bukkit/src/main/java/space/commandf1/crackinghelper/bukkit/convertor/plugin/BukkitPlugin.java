package space.commandf1.crackinghelper.bukkit.convertor.plugin;

import lombok.val;
import org.bukkit.plugin.Plugin;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.PluginDescription;

/**
 * @author commandf1
 */
public class BukkitPlugin extends CommonPlugin<Plugin> {
    public BukkitPlugin(Plugin plugin) {
        super(plugin);
    }

    @Override
    public PluginDescription getDescription() {
        val description = this.getPlugin().getDescription();
        return new PluginDescription(
                description.getName(),
                description.getMain(),
                description.getVersion()
        );
    }

    @Override
    public String getName() {
        return this.getPlugin().getName();
    }
}
