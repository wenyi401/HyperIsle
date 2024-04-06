package art.luaj.hyperisle.plugin;

import android.content.Context;
import android.content.pm.PackageManager;

import art.luaj.hyperisle.BuildConfig;
import art.luaj.hyperisle.ext.XLog;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class PluginController implements PluginInfo {
    private Context context;
    private Context modContext;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private InitPlugin initPlugin;

    public PluginController(Context context, XC_LoadPackage.LoadPackageParam loadPackageParam) {
        this.context = context;
        this.loadPackageParam = loadPackageParam;
        try {
            if (this.modContext == null) {
                this.modContext = this.context.createPackageContext(BuildConfig.APPLICATION_ID,
                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            }
        } catch (PackageManager.NameNotFoundException e) {
            XLog.print(e.toString());
        }
        this.initPlugin = new InitPlugin(this);
    }

    public void init() {
        this.initPlugin.init();
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public Context getModContext() {
        return this.modContext;
    }

    @Override
    public XC_LoadPackage.LoadPackageParam getLoadParam() {
        return this.loadPackageParam;
    }

    @Override
    public InitPlugin initPlugin()  {
        return this.initPlugin;
    }

    @Override
    public Class<?> findClass(String className) {
        try {
            return this.loadPackageParam.classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            XLog.print(e.toString());
            return null;
        }
    }
}
