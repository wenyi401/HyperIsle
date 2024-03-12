package art.luaj.hyperisle.plugin;

import java.util.ArrayList;

import art.luaj.hyperisle.plugin.Media.MediaPlugin;
import art.luaj.hyperisle.plugin.Notify.NotifyPlugin;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;

public class InitPlugin {
    public InitPlugin() {

    }

    public static InitPlugin with() {
        return new InitPlugin();
    }

    public ArrayList<BasePlugin> getPlugins() {
        ArrayList<BasePlugin> plugins = new ArrayList<BasePlugin>();
        plugins.add(new MediaPlugin()); // add
        plugins.add(new NotifyPlugin()); // add
        plugins.add(new StrongToastPlugin()); //add
        return plugins;
    }
}
