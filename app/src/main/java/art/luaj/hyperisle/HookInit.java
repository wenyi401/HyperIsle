package art.luaj.hyperisle;

import android.content.Context;
import android.content.res.XModuleResources;
import android.os.Build;
import android.view.WindowManager;

import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.ext.XLog;
import art.luaj.hyperisle.ext.XResources;
import art.luaj.hyperisle.plugin.PluginController;
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
    private String MODULE_CLASS;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private Context mContext;

    public static XSharedPreferences getSharedPreferences(String key) {
        XSharedPreferences sp = new XSharedPreferences(BuildConfig.APPLICATION_ID, key);
        sp.makeWorldReadable();
        return sp;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        if (packageName.equals(Config.SystemUiPackage)) {
            this.loadPackageParam = loadPackageParam;
            MODULE_SP = getSharedPreferences(Config.DEFAULT_STORAGE);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                MODULE_CLASS = Config.DaggerReferenceGlobalRootComponent;
            } else {
                MODULE_CLASS = Config.DaggerGlobalRootComponent;
            }
            XposedHelpers.findAndHookMethod(
                findClass(MODULE_CLASS),
                "mainResources",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        mContext = (Context) XposedHelpers.getObjectField(param.thisObject, "context");
                        PluginController pluginController = new PluginController(mContext, loadPackageParam);
                        pluginController.init();
                    }
                });
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        this.MODULE_PATH = startupParam.modulePath;
        XResources.init(XModuleResources.createInstance(this.MODULE_PATH, null));
    }

    private Class<?> getClass(XC_LoadPackage.LoadPackageParam lpparam, String classname) {
        try {
            return lpparam.classLoader.loadClass(classname);
        } catch (ClassNotFoundException e) {
            XLog.print(e.toString());
            return null;
        }
    }

    public Class<?> findClass(String classname) {
        return getClass(this.loadPackageParam, classname);
    }

}
