package space.commandf1.crackinghelper.velocity.convertor.plugin;

import com.velocitypowered.api.plugin.PluginContainer;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.PluginDescription;
import space.commandf1.crackinghelper.velocity.util.VelocityUtil;

/**
 * @author commandf1
 */
public class VelocityPlugin extends CommonPlugin<PluginContainer> {
    public VelocityPlugin(PluginContainer plugin) {
        super(plugin);
    }

    @Override
    public PluginDescription getDescription() {
        return VelocityUtil.getPluginDescription(this.getPlugin());
    }

    @Override
    public String getName() {
        return this.getPlugin().getDescription().getId();
    }
}
