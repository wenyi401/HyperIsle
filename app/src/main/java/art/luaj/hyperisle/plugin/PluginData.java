package art.luaj.hyperisle.plugin;

import java.util.ArrayList;

import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.plugin.Battery.BatteryPlugin;
import art.luaj.hyperisle.plugin.Media.MediaPlugin;
import art.luaj.hyperisle.plugin.Notify.NotifyPlugin;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;

public class PluginData {

    /**
     * 获取插件内容
     *
     * @return plugins 插件列表
     */
    public static ArrayList<BasePlugin> getPlugins() {
        ArrayList<BasePlugin> plugins = new ArrayList<BasePlugin>();
        plugins.add(new MediaPlugin()); // add
        plugins.add(new NotifyPlugin()); // add
        plugins.add(new StrongToastPlugin()); //add
        plugins.add(new BatteryPlugin());
        return plugins;
    }

}
