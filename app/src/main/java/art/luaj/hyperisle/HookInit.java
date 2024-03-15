package art.luaj.hyperisle;

import android.content.Context;
import android.content.res.XModuleResources;
import android.os.Build;

import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.ext.XResources;
import art.luaj.hyperisle.plugin.InitPlugin;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInit implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private String MODULE_PATH;
    private XSharedPreferences MODULE_SP;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        this.loadPackageParam = loadPackageParam;
        if (packageName.equals(Config.SystemUiPackage)) {
            this.MODULE_SP = getSharedPreferences(Config.DEFAULT_STORAGE);
            XModuleResources res = XModuleResources.createInstance(MODULE_PATH, null);
            XResources.init(res); // init

            InitPlugin plugins = InitPlugin.with(loadPackageParam);
            plugins.setXSharedPre(this.MODULE_SP);
            plugins.init();
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        this.MODULE_PATH = startupParam.modulePath;
    }

    public static XSharedPreferences getSharedPreferences(String key) {
        XSharedPreferences sp = new XSharedPreferences(BuildConfig.APPLICATION_ID, key);
        sp.makeWorldReadable();
        return sp;
    }
}
