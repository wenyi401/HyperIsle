package art.luaj.hyperisle.plugin;

import java.util.ArrayList;

import art.luaj.hyperisle.plugin.Media.MediaPlugin;
import art.luaj.hyperisle.plugin.Notify.NotifyPlugin;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitPlugin {
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    public InitPlugin(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    public static InitPlugin with(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        return new InitPlugin(loadPackageParam);
    }

    public ArrayList<BasePlugin> getPlugins() {
        ArrayList<BasePlugin> plugins = new ArrayList<BasePlugin>();
        plugins.add(new MediaPlugin()); // add
        plugins.add(new NotifyPlugin()); // add
        plugins.add(new StrongToastPlugin()); //add
        return plugins;
    }
}
