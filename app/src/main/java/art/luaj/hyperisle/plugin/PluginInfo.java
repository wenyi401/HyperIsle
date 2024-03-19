package art.luaj.hyperisle.plugin;

import android.content.Context;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public interface PluginInfo {
    Context getContext();

    Context getModContext();

    XC_LoadPackage.LoadPackageParam getLoadParam();

    InitPlugin initPlugin();

    Class<?> findClass(String className);
}
