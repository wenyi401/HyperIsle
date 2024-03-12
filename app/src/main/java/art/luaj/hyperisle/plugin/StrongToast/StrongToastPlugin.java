package art.luaj.hyperisle.plugin.StrongToast;

import art.luaj.hyperisle.plugin.BasePlugin;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StrongToastPlugin extends BasePlugin {

    @Override
    public void loadHook(XC_LoadPackage.LoadPackageParam packageParam) {

    }

    @Override
    public String getName() {
        return "StrongToastPlugin";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void onClick() {

    }
}
