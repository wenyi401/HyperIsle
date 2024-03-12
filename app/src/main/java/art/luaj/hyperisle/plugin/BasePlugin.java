package art.luaj.hyperisle.plugin;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class BasePlugin {
    public abstract void loadHook(XC_LoadPackage.LoadPackageParam packageParam);

    public abstract String getName();

    public abstract String getDescription();

    public abstract void onClick();

    public void onLeftClick() {
    }

    public void onRightClick() {}

}
