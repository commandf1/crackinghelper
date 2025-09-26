package space.commandf1.crackinghelper.velocity.util;

import com.velocitypowered.api.plugin.PluginContainer;
import lombok.val;
import space.commandf1.crackinghelper.common.convertor.plugin.PluginDescription;

/**
 * @author commandf1
 */
public class VelocityUtil {
    public static PluginDescription getPluginDescription(PluginContainer plugin) {
        if (plugin.getInstance().isEmpty()) {
            return null;
        }

        val instance = plugin.getInstance().get();

        return new PluginDescription(
                plugin.getDescription().getName().orElse("Unknown"),
                instance.getClass().getName(),
                plugin.getDescription().getVersion().orElse("Unknown")
        );
    }
}
