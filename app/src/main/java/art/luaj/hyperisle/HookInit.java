package art.luaj.hyperisle;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.XModuleResources;
import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;

import androidx.annotation.Nullable;

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
    private String MODULE_CLASS;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    public static Context mContext;
    public static WindowManager.LayoutParams mWindow;
    public static Context modContext;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        this.loadPackageParam = loadPackageParam;
        if (packageName.equals(Config.SystemUiPackage)) {
            //initWindow();
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
                        try {
                            if (modContext  == null) {
                                modContext = HookInit.mContext.createPackageContext(BuildConfig.APPLICATION_ID,
                                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            XposedBridge.log("失败");
                        }
                        InitPlugin plugins = InitPlugin.with(loadPackageParam);
                        plugins.setXSharedPre(MODULE_SP);
                        plugins.init();
                    }
                });
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

    public static Class<?> getClass(XC_LoadPackage.LoadPackageParam lpparam, String classname) {
        try {
            return lpparam.classLoader.loadClass(classname);
        } catch (ClassNotFoundException e) {
            XposedBridge.log(e);
            e.printStackTrace();
            return null;
        }
    }

    public Class<?> findClass(String classname) {
        return getClass(this.loadPackageParam, classname);
    }

}
