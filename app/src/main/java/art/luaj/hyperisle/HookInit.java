package art.luaj.hyperisle;

import android.content.res.XModuleResources;

import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.ext.XResources;
import art.luaj.hyperisle.plugin.InitPlugin;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInit implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private String MODULE_PATH = null;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        if (packageName.equals(Config.SystemUiPackage)) {
            XModuleResources res = XModuleResources.createInstance(MODULE_PATH, null);
            XResources.init(res); // init

            InitPlugin plugins = InitPlugin.with(loadPackageParam);
            plugins.getPlugins();
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        this.MODULE_PATH = startupParam.modulePath;
    }
}
