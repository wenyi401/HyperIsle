package art.luaj.hyperisle.plugin.StrongToast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import art.luaj.hyperisle.HookInit;
import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Config;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StrongToastPlugin extends BasePlugin {
    private Context mContext;
    private XC_LoadPackage.LoadPackageParam mPackageParam;
    private View mView;

    @Override
    public String getName() {
        return "StrongToastPlugin";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public View onBind() {
        return null;
    }

    @Override
    public void onCreate(Context context, XC_LoadPackage.LoadPackageParam packageParam) {
        this.mContext = context;
        this.mPackageParam = packageParam;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_notify, null);
        XC_MethodHook methodHook =  new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        };

        XposedHelpers.findAndHookMethod(
            findClass(Config.StrongToast),
            "setValue",
            methodHook
        );
    }

    @Override
    public void onClick() {

    }

    private Class<?> findClass(String classname) {
        return HookInit.getClass(this.mPackageParam, classname);
    }
}
